package com.example.chessgame.activities

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chessgame.*
import com.example.chessgame.databinding.ActivityOneDeviceGameBinding
import com.example.chessgame.pieces.*
import com.example.chessgame.shareddata.MovementOfKingAndRook1Device

class OneDeviceGameActivity : AppCompatActivity(), View.OnClickListener {

    private var mBinding: ActivityOneDeviceGameBinding? = null

    private val binding get() = mBinding!!

    private lateinit var clickedTilePosition: Position

    private var isValidTouch = true
    private var isWhiteTurn = false
    private var canEnpassant = false
    private val enpassantPosition = HashSet<Position>()
    private var enpassantPawnPosition = Position(-1, -1)
    private var isCheck = false
    private var killOneself = false

    private var boardPosition = Array(8) {
        Array<Piece>(8) {
            Empty()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val onMoveListener = View.OnClickListener {
        val intId = it.id
        val id = resources.getResourceEntryName(intId)
        val x = Integer.parseInt(id[2].toString())
        val y = Integer.parseInt(id[3].toString())

        val clickedTileX = clickedTilePosition.x
        val clickedTileY = clickedTilePosition.y

        val touchPiece = boardPosition[x][y]

        isValidTouch = true

        if (touchPiece.onCanMove) {
            val clickedTileType = pieceKind(boardPosition[clickedTileX][clickedTileY])
            val clickedTileColor = when (clickedTileType) {
                "Pawn" -> (boardPosition[clickedTileX][clickedTileY] as Pawn).colorId
                "Rook" -> (boardPosition[clickedTileX][clickedTileY] as Rook).colorId
                "Knight" -> (boardPosition[clickedTileX][clickedTileY] as Knight).colorId
                "Bishop" -> (boardPosition[clickedTileX][clickedTileY] as Bishop).colorId
                "Queen" -> (boardPosition[clickedTileX][clickedTileY] as Queen).colorId
                "King" -> (boardPosition[clickedTileX][clickedTileY] as King).colorId
                else -> true
            }

            val enpassantIter = enpassantPosition.iterator()
            while (enpassantIter.hasNext()) {
                val enpassant = enpassantIter.next()

                if (enpassant == Position(x, y)) {
                    if (x == 5) {
                        boardPosition[x - 1][y] = Empty()
                    } else if (x == 2) {
                        boardPosition[x + 1][y] = Empty()
                    }
                }
            }

            if (x == clickedTileX && y == clickedTileY) {
                isValidTouch = false
            } else if (clickedTileType == "King") {
                if (clickedTileColor) {
                    MovementOfKingAndRook1Device.isWhiteKingMoved = true
                } else {
                    MovementOfKingAndRook1Device.isBlackKingMoved = true
                }
            } else if (clickedTileType == "Rook") {
                if (y == 0) {
                    if (clickedTileColor) {
                        MovementOfKingAndRook1Device.isWhiteLeftRookMoved = true
                    } else {
                        MovementOfKingAndRook1Device.isBlackLeftRookMoved = true
                    }
                } else if (y == 7) {
                    if (clickedTileColor) {
                        MovementOfKingAndRook1Device.isWhiteRightRookMoved = true
                    } else {
                        MovementOfKingAndRook1Device.isBlackRightRookMoved = true
                    }
                }
            }

            if (MovementOfKingAndRook1Device.whiteKSC) {
                if (x == 7 && y == 6) {
                    boardPosition[7][7] = Empty()
                    boardPosition[7][5] = Rook(true)
                }
                MovementOfKingAndRook1Device.whiteKSC = false
            }
            if (MovementOfKingAndRook1Device.whiteQSC) {
                if (x == 7 && y == 2) {
                    boardPosition[7][0] = Empty()
                    boardPosition[7][3] = Rook(true)
                }
                MovementOfKingAndRook1Device.whiteQSC = false
            }
            if (MovementOfKingAndRook1Device.blackKSC) {
                if (x == 0 && y == 6) {
                    boardPosition[0][7] = Empty()
                    boardPosition[0][5] = Rook(false)
                }
                MovementOfKingAndRook1Device.blackKSC = false
            }
            if (MovementOfKingAndRook1Device.blackQSC) {
                if (x == 0 && y == 2) {
                    boardPosition[0][0] = Empty()
                    boardPosition[0][3] = Rook(false)
                }
                MovementOfKingAndRook1Device.blackQSC = false
            }

            val enpassant = DetailedRules(boardPosition).enpassantManager()
            if (enpassant.isNotEmpty()) {
                val iterator = enpassant.iterator()
                while (iterator.hasNext()) {
                    val enpassantPair = iterator.next()
                    val first = enpassantPair.first
                    val second = enpassantPair.second

                    if (first.x == 6) {
                        if (x == first.x - 2 && y == first.y) {
                            canEnpassant = true
                            enpassantPawnPosition = second
                            enpassantPosition.add(Position(first.x - 1, first.y))
                        }
                    } else if (first.x == 1) {
                        if (x == first.x + 2 && y == first.y) {
                            canEnpassant = true
                            enpassantPawnPosition = second
                            enpassantPosition.add(Position(first.x + 1, first.y))
                        }
                    }
                }
            }

            boardPosition[clickedTileX][clickedTileY] = Empty()

            when (clickedTileType) {
                "Pawn" -> boardPosition[x][y] = Pawn(clickedTileColor)
                "Rook" -> boardPosition[x][y] = Rook(clickedTileColor)
                "Knight" -> boardPosition[x][y] = Knight(clickedTileColor)
                "Bishop" -> boardPosition[x][y] = Bishop(clickedTileColor)
                "Queen" -> boardPosition[x][y] = Queen(clickedTileColor)
                "King" -> boardPosition[x][y] = King(clickedTileColor)
            }

            if (isCheck) {
                val wKingPos = DetailedRules(boardPosition).getKingPosition(true)
                val bKingPos = DetailedRules(boardPosition).getKingPosition(false)
                val stillKingInDangerWhite =
                    DetailedRules(boardPosition).isCheck(wKingPos.x, wKingPos.y, true)
                val stillKingInDangerBlack =
                    DetailedRules(boardPosition).isCheck(bKingPos.x, bKingPos.y, false)
                if (stillKingInDangerWhite || stillKingInDangerBlack) {
                    if (x != clickedTileX || y != clickedTileY) {
                        val outMetrix = DisplayMetrics()
                        windowManager.defaultDisplay.getMetrics(outMetrix)
                        if (clickedTileColor) {
                            val cannotMoveView =
                                View.inflate(
                                    this@OneDeviceGameActivity,
                                    R.layout.toast_cannotmove,
                                    null
                                )
                            val toast = Toast(this@OneDeviceGameActivity)
                            toast.view = cannotMoveView
                            toast.setGravity(Gravity.CENTER, 0, 320 * outMetrix.density.toInt())
                            toast.duration = Toast.LENGTH_SHORT
                            toast.show()
                        } else {
                            val cannotMoveView = View.inflate(
                                this@OneDeviceGameActivity,
                                R.layout.toast_cannotmove,
                                null
                            )
                            cannotMoveView.rotation = 180F
                            val toast = Toast(this@OneDeviceGameActivity)
                            toast.view = cannotMoveView
                            toast.setGravity(Gravity.CENTER, 0, -270 * outMetrix.density.toInt())
                            toast.duration = Toast.LENGTH_SHORT
                            toast.show()
                        }
                    }

                    boardPosition[x][y] = Empty()
                    when (clickedTileType) {
                        "Pawn" -> boardPosition[clickedTileX][clickedTileY] = Pawn(clickedTileColor)
                        "Rook" -> boardPosition[clickedTileX][clickedTileY] = Rook(clickedTileColor)
                        "Knight" -> boardPosition[clickedTileX][clickedTileY] =
                            Knight(clickedTileColor)
                        "Bishop" -> boardPosition[clickedTileX][clickedTileY] =
                            Bishop(clickedTileColor)
                        "Queen" -> boardPosition[clickedTileX][clickedTileY] =
                            Queen(clickedTileColor)
                        "King" -> boardPosition[clickedTileX][clickedTileY] = King(clickedTileColor)
                    }
                    setListener()
                } else {
                    isCheck = false
                }
            }

            val wKingPos = DetailedRules(boardPosition).getKingPosition(true)
            val bKingPos = DetailedRules(boardPosition).getKingPosition(false)

            if (isWhiteTurn) {
                if (DetailedRules(boardPosition).isCheck(wKingPos.x, wKingPos.y, true)) {
                    killOneself = true
                }
            } else {
                if (DetailedRules(boardPosition).isCheck(bKingPos.x, bKingPos.y, false)) {
                    killOneself = true
                }
            }

            if (killOneself) {
                if (x != clickedTileX || y != clickedTileY) {
                    val outMetrix = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(outMetrix)
                    if (clickedTileColor) {
                        val cannotMoveView =
                            View.inflate(
                                this@OneDeviceGameActivity,
                                R.layout.toast_cannotmove,
                                null
                            )
                        val toast = Toast(this@OneDeviceGameActivity)
                        toast.view = cannotMoveView
                        toast.setGravity(Gravity.CENTER, 0, 320 * outMetrix.density.toInt())
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
                    } else {
                        val cannotMoveView = View.inflate(
                            this@OneDeviceGameActivity,
                            R.layout.toast_cannotmove,
                            null
                        )
                        cannotMoveView.rotation = 180F
                        val toast = Toast(this@OneDeviceGameActivity)
                        toast.view = cannotMoveView
                        toast.setGravity(Gravity.CENTER, 0, -270 * outMetrix.density.toInt())
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
                    }
                }

                boardPosition[x][y] = Empty()
                when (clickedTileType) {
                    "Pawn" -> boardPosition[clickedTileX][clickedTileY] = Pawn(clickedTileColor)
                    "Rook" -> boardPosition[clickedTileX][clickedTileY] = Rook(clickedTileColor)
                    "Knight" -> boardPosition[clickedTileX][clickedTileY] =
                        Knight(clickedTileColor)
                    "Bishop" -> boardPosition[clickedTileX][clickedTileY] =
                        Bishop(clickedTileColor)
                    "Queen" -> boardPosition[clickedTileX][clickedTileY] =
                        Queen(clickedTileColor)
                    "King" -> boardPosition[clickedTileX][clickedTileY] = King(clickedTileColor)
                }
                setListener()
            }

            if (!isCheck && !killOneself) {
                val detailedRules = DetailedRules(boardPosition)

                val opposingKingPos = detailedRules.getKingPosition(!clickedTileColor)

                val isKingInDanger =
                    detailedRules.isCheck(opposingKingPos.x, opposingKingPos.y, !clickedTileColor)
                if (isKingInDanger) {
                    isCheck = true
                    if (detailedRules.isCheckMate(clickedTileColor)) {
                        if (!clickedTileColor) {
                            val checkMateView =
                                View.inflate(
                                    this@OneDeviceGameActivity,
                                    R.layout.checkmate_dialog,
                                    null
                                )
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(checkMateView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 500)

                            val okButton = findViewById<Button>(R.id.checkmate_okButton)
                            okButton.setOnClickListener {
                                dialog.dismiss()
                            }
                        } else {
                            val checkMateView =
                                View.inflate(
                                    this@OneDeviceGameActivity,
                                    R.layout.checkmate_dialog,
                                    null
                                )
                            checkMateView.rotation = 180F
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(checkMateView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 500)
                            val okButton =
                                checkMateView.findViewById<Button>(R.id.checkmate_okButton)
                            okButton.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    } else {
                        if (!clickedTileColor) {
                            val checkView =
                                View.inflate(
                                    this@OneDeviceGameActivity,
                                    R.layout.check_dialog,
                                    null
                                )
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(checkView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 360)

                            val okButton: TextView =
                                checkView.findViewById<Button>(R.id.check_okButton)
                            okButton.setOnClickListener {
                                dialog.dismiss()
                            }
                        } else {
                            val checkView =
                                View.inflate(
                                    this@OneDeviceGameActivity,
                                    R.layout.check_dialog,
                                    null
                                )
                            checkView.rotation = 180F
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(checkView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 360)

                            val okButton: TextView =
                                checkView.findViewById<Button>(R.id.check_okButton)
                            okButton.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    }
                } else {
                    if (DetailedRules(boardPosition).isStaleMate(!clickedTileColor)) {
                        if (!clickedTileColor) {
                            val staleMateView =
                                View.inflate(
                                    this@OneDeviceGameActivity,
                                    R.layout.stalemate_dialog,
                                    null
                                )
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(staleMateView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 500)

                            val okButton: TextView = findViewById<Button>(R.id.stalemate_okButton)
                            okButton.setOnClickListener {
                                dialog.dismiss()
                            }
                        } else {
                            val staleMateView =
                                View.inflate(
                                    this@OneDeviceGameActivity,
                                    R.layout.stalemate_dialog,
                                    null
                                )
                            staleMateView.rotation = 180F
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(staleMateView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 500)

                            val okButton: TextView =
                                staleMateView.findViewById<Button>(R.id.stalemate_okButton)
                            okButton.setOnClickListener {
                                dialog.dismiss()
                            }
                        }
                    }
                }
            }

            killOneself = false

            pawnPromotion()

            updateUi()
            resetBoardColor()
            setListener()
        } else {
            isValidTouch = false
            resetBoardColor()
            setListener()
        }

        for (i in 0..7) {
            for (j in 0..7) {
                boardPosition[i][j].onCanMove = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityOneDeviceGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        boardInitialize()
        setListener()
    }

    private fun boardInitialize() {

        boardPosition[0][0] = Rook(false)
        boardPosition[0][1] = Knight(false)
        boardPosition[0][2] = Bishop(false)
        boardPosition[0][3] = Queen(false)
        boardPosition[0][4] = King(false)
        boardPosition[0][5] = Bishop(false)
        boardPosition[0][6] = Knight(false)
        boardPosition[0][7] = Rook(false)

        boardPosition[1][0] = Pawn(false)
        boardPosition[1][1] = Pawn(false)
        boardPosition[1][2] = Pawn(false)
        boardPosition[1][3] = Pawn(false)
        boardPosition[1][4] = Pawn(false)
        boardPosition[1][5] = Pawn(false)
        boardPosition[1][6] = Pawn(false)
        boardPosition[1][7] = Pawn(false)

        boardPosition[2][0] = Empty()
        boardPosition[2][1] = Empty()
        boardPosition[2][2] = Empty()
        boardPosition[2][3] = Empty()
        boardPosition[2][4] = Empty()
        boardPosition[2][5] = Empty()
        boardPosition[2][6] = Empty()
        boardPosition[2][7] = Empty()

        boardPosition[3][0] = Empty()
        boardPosition[3][1] = Empty()
        boardPosition[3][2] = Empty()
        boardPosition[3][3] = Empty()
        boardPosition[3][4] = Empty()
        boardPosition[3][5] = Empty()
        boardPosition[3][6] = Empty()
        boardPosition[3][7] = Empty()

        boardPosition[4][0] = Empty()
        boardPosition[4][1] = Empty()
        boardPosition[4][2] = Empty()
        boardPosition[4][3] = Empty()
        boardPosition[4][4] = Empty()
        boardPosition[4][5] = Empty()
        boardPosition[4][6] = Empty()
        boardPosition[4][7] = Empty()

        boardPosition[5][0] = Empty()
        boardPosition[5][1] = Empty()
        boardPosition[5][2] = Empty()
        boardPosition[5][3] = Empty()
        boardPosition[5][4] = Empty()
        boardPosition[5][5] = Empty()
        boardPosition[5][6] = Empty()
        boardPosition[5][7] = Empty()

        boardPosition[6][0] = Pawn(true)
        boardPosition[6][1] = Pawn(true)
        boardPosition[6][2] = Pawn(true)
        boardPosition[6][3] = Pawn(true)
        boardPosition[6][4] = Pawn(true)
        boardPosition[6][5] = Pawn(true)
        boardPosition[6][6] = Pawn(true)
        boardPosition[6][7] = Pawn(true)

        boardPosition[7][0] = Rook(true)
        boardPosition[7][1] = Knight(true)
        boardPosition[7][2] = Bishop(true)
        boardPosition[7][3] = Queen(true)
        boardPosition[7][4] = King(true)
        boardPosition[7][5] = Bishop(true)
        boardPosition[7][6] = Knight(true)
        boardPosition[7][7] = Rook(true)

        updateUi()
    }

    private fun updateUi() {
        for (i in 0..7) {
            for (j in 0..7) {
                val pieceKind = pieceKind(boardPosition[i][j])
                val textViewId: Int = resources.getIdentifier("oP$i$j", "id", packageName)
                val textView: TextView = findViewById(textViewId)
                when (pieceKind) {
                    "Pawn" -> (boardPosition[i][j] as Pawn).setDrawable(textView)
                    "Rook" -> (boardPosition[i][j] as Rook).setDrawable(textView)
                    "Knight" -> (boardPosition[i][j] as Knight).setDrawable(textView)
                    "Bishop" -> (boardPosition[i][j] as Bishop).setDrawable(textView)
                    "Queen" -> (boardPosition[i][j] as Queen).setDrawable(textView)
                    "King" -> (boardPosition[i][j] as King).setDrawable(textView)
                    "Empty" -> (boardPosition[i][j] as Empty).setDrawable(textView)
                }
            }
        }
    }

    private fun pieceKind(boardPosition: Piece): String {
        return when (boardPosition) {
            is Pawn -> {
                "Pawn"
            }
            is Rook -> {
                "Rook"
            }
            is Knight -> {
                "Knight"
            }
            is Bishop -> {
                "Bishop"
            }
            is Queen -> {
                "Queen"
            }
            is King -> {
                "King"
            }
            else -> {
                "Empty"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClick(v: View?) {
        var canEatPosition = HashSet<Position>()
        val intId = v?.id
        val id = intId?.let { resources.getResourceEntryName(it) }
        android.util.Log.d("ChessGame", id.toString())
        val x = Integer.parseInt(id?.get(2).toString())
        val y = Integer.parseInt(id?.get(3).toString())

        val position = Position(x, y)
        clickedTilePosition = position
        var canMovePositions = HashSet<Position>()

        for (i in 0..7) {
            for (j in 0..7) {
                boardPosition[x][y].onCanMove = false
            }
        }

        when (pieceKind(boardPosition[x][y])) {
            "Pawn" -> canMovePositions =
                (boardPosition[x][y] as Pawn).getCanMoveArea(position, boardPosition, isWhiteTurn)
            "Rook" -> canMovePositions =
                (boardPosition[x][y] as Rook).getCanMoveArea(position, boardPosition, isWhiteTurn)
            "Knight" -> canMovePositions =
                (boardPosition[x][y] as Knight).getCanMoveArea(position, boardPosition, isWhiteTurn)
            "Bishop" -> canMovePositions =
                (boardPosition[x][y] as Bishop).getCanMoveArea(position, boardPosition, isWhiteTurn)
            "Queen" -> canMovePositions =
                (boardPosition[x][y] as Queen).getCanMoveArea(position, boardPosition, isWhiteTurn)
            "King" -> canMovePositions =
                (boardPosition[x][y] as King).getCanMoveArea(position, boardPosition, isWhiteTurn)
            "Empty" -> canMovePositions =
                (boardPosition[x][y] as Empty).getCanMoveArea(position, boardPosition)
        }

        val castlingPositionSet: HashSet<Position>? = DetailedRules(boardPosition).castlingManager()
        if (castlingPositionSet != null) {
            val castlingIter = castlingPositionSet.iterator()
            while (castlingIter.hasNext()) {
                val castlingPosition = castlingIter.next()
                if (boardPosition[x][y] is King) {
                    if (castlingPosition.x == 0) {
                        if (!isWhiteTurn && x == 0) {
                            canMovePositions.add(castlingPosition)
                        }
                    } else if (castlingPosition.x == 7) {
                        if (isWhiteTurn && x == 7) {
                            canMovePositions.add(castlingPosition)
                        }
                    }
                }
            }
        }

        val iter: Iterator<Position> = canMovePositions.iterator()
        while (iter.hasNext()) {
            val pos = iter.next()
            val x = pos.x
            val y = pos.y
            val textViewId = resources.getIdentifier("oB$x$y", "id", packageName)
            android.util.Log.d("ChessGame", "oB$x$y")
            val textView: TextView = findViewById(textViewId)
            textView.setBackgroundColor(resources.getColor(R.color.CANGO_BOARD))
            boardPosition[x][y].onCanMove = true
        }


        when (pieceKind(boardPosition[x][y])) {
            "Pawn" -> {
                canEatPosition =
                    (boardPosition[x][y] as Pawn).isCanEat(position, boardPosition, isWhiteTurn)
            }
            "Rook" -> {
                canEatPosition =
                    (boardPosition[x][y] as Rook).isCanEat(position, boardPosition, isWhiteTurn)
            }
            "Knight" -> {
                canEatPosition =
                    (boardPosition[x][y] as Knight).isCanEat(position, boardPosition, isWhiteTurn)
            }
            "Bishop" -> {
                canEatPosition =
                    (boardPosition[x][y] as Bishop).isCanEat(position, boardPosition, isWhiteTurn)
            }
            "Queen" -> {
                canEatPosition =
                    (boardPosition[x][y] as Queen).isCanEat(position, boardPosition, isWhiteTurn)
            }
            "King" -> {
                canEatPosition =
                    (boardPosition[x][y] as King).isCanEat(position, boardPosition, isWhiteTurn)
            }
        }

        if (canMovePositions.isNotEmpty()) {
            val enpassantIterator = enpassantPosition.iterator()
            while (enpassantIterator.hasNext()) {
                if (canEnpassant) {
                    val enpassant = enpassantIterator.next()
                    if (Position(x, y) == enpassantPawnPosition) {
                        canEatPosition.add(enpassant)
                    }
                }
            }
        }

        getCanEatTiles(canEatPosition)
        val canEatPositionIter: Iterator<Position> = canEatPosition.iterator()
        while (canEatPositionIter.hasNext()) {
            val pos = canEatPositionIter.next()
            val x = pos.x
            val y = pos.y
            boardPosition[x][y].onCanMove = true
        }

        canEatPosition.clear()
        if (canMovePositions.isNotEmpty()) {
            setMoveListener()
            canMovePositions.clear()
        }
    }

    private fun getCanEatTiles(targetPos: HashSet<Position>) {
        val iter = targetPos.iterator()
        while (iter.hasNext()) {
            val pos = iter.next()
            val x = pos.x
            val y = pos.y
            val id = resources.getIdentifier("oB$x$y", "id", packageName)
            val textView: TextView = findViewById(id)
            textView.setBackgroundColor(resources.getColor(R.color.CANEAT_BOARD))
        }
    }

    private fun pawnPromotion() {
        var x = 0
        for (y in 0..7) {
            if (boardPosition[x][y] is Pawn) {
                whitePawnPromotion(x, y)
            }
        }

        x = 7
        for (y in 0..7) {
            if (boardPosition[x][y] is Pawn) {
                blackPawnPromotion(x, y)
            }
        }
    }

    private fun whitePawnPromotion(x: Int, y: Int) {
        val dialog = WhitePawnPromotionDialog(this)
        dialog.setDialogListener(object : WhiteDialogListener {
            override fun onClicked(type: Piece) {
                boardPosition[x][y] = type
                updateUi()
            }
        })
        dialog.show()
    }

    private fun blackPawnPromotion(x: Int, y: Int) {
        val dialog = BlackPawnPromotionDialog(this)
        dialog.setDialogListener(object : BlackDialogListener {
            override fun onClicked(type: Piece) {
                boardPosition[x][y] = type
                updateUi()
            }
        })
        dialog.show()
    }

    private fun setListener() {
        binding.oP00.setOnClickListener(this)
        binding.oP01.setOnClickListener(this)
        binding.oP02.setOnClickListener(this)
        binding.oP03.setOnClickListener(this)
        binding.oP04.setOnClickListener(this)
        binding.oP05.setOnClickListener(this)
        binding.oP06.setOnClickListener(this)
        binding.oP07.setOnClickListener(this)
        binding.oP10.setOnClickListener(this)
        binding.oP11.setOnClickListener(this)
        binding.oP12.setOnClickListener(this)
        binding.oP13.setOnClickListener(this)
        binding.oP14.setOnClickListener(this)
        binding.oP15.setOnClickListener(this)
        binding.oP16.setOnClickListener(this)
        binding.oP17.setOnClickListener(this)
        binding.oP20.setOnClickListener(this)
        binding.oP21.setOnClickListener(this)
        binding.oP22.setOnClickListener(this)
        binding.oP23.setOnClickListener(this)
        binding.oP24.setOnClickListener(this)
        binding.oP25.setOnClickListener(this)
        binding.oP26.setOnClickListener(this)
        binding.oP27.setOnClickListener(this)
        binding.oP30.setOnClickListener(this)
        binding.oP31.setOnClickListener(this)
        binding.oP32.setOnClickListener(this)
        binding.oP33.setOnClickListener(this)
        binding.oP34.setOnClickListener(this)
        binding.oP35.setOnClickListener(this)
        binding.oP36.setOnClickListener(this)
        binding.oP37.setOnClickListener(this)
        binding.oP40.setOnClickListener(this)
        binding.oP41.setOnClickListener(this)
        binding.oP42.setOnClickListener(this)
        binding.oP43.setOnClickListener(this)
        binding.oP44.setOnClickListener(this)
        binding.oP45.setOnClickListener(this)
        binding.oP46.setOnClickListener(this)
        binding.oP47.setOnClickListener(this)
        binding.oP50.setOnClickListener(this)
        binding.oP51.setOnClickListener(this)
        binding.oP52.setOnClickListener(this)
        binding.oP53.setOnClickListener(this)
        binding.oP54.setOnClickListener(this)
        binding.oP55.setOnClickListener(this)
        binding.oP56.setOnClickListener(this)
        binding.oP57.setOnClickListener(this)
        binding.oP60.setOnClickListener(this)
        binding.oP61.setOnClickListener(this)
        binding.oP62.setOnClickListener(this)
        binding.oP63.setOnClickListener(this)
        binding.oP64.setOnClickListener(this)
        binding.oP65.setOnClickListener(this)
        binding.oP66.setOnClickListener(this)
        binding.oP67.setOnClickListener(this)
        binding.oP70.setOnClickListener(this)
        binding.oP71.setOnClickListener(this)
        binding.oP72.setOnClickListener(this)
        binding.oP73.setOnClickListener(this)
        binding.oP74.setOnClickListener(this)
        binding.oP75.setOnClickListener(this)
        binding.oP76.setOnClickListener(this)
        binding.oP77.setOnClickListener(this)

        turnManager()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setMoveListener() {
        binding.oP00.setOnClickListener(onMoveListener)
        binding.oP01.setOnClickListener(onMoveListener)
        binding.oP02.setOnClickListener(onMoveListener)
        binding.oP03.setOnClickListener(onMoveListener)
        binding.oP04.setOnClickListener(onMoveListener)
        binding.oP05.setOnClickListener(onMoveListener)
        binding.oP06.setOnClickListener(onMoveListener)
        binding.oP07.setOnClickListener(onMoveListener)
        binding.oP10.setOnClickListener(onMoveListener)
        binding.oP11.setOnClickListener(onMoveListener)
        binding.oP12.setOnClickListener(onMoveListener)
        binding.oP13.setOnClickListener(onMoveListener)
        binding.oP14.setOnClickListener(onMoveListener)
        binding.oP15.setOnClickListener(onMoveListener)
        binding.oP16.setOnClickListener(onMoveListener)
        binding.oP17.setOnClickListener(onMoveListener)
        binding.oP20.setOnClickListener(onMoveListener)
        binding.oP21.setOnClickListener(onMoveListener)
        binding.oP22.setOnClickListener(onMoveListener)
        binding.oP23.setOnClickListener(onMoveListener)
        binding.oP24.setOnClickListener(onMoveListener)
        binding.oP25.setOnClickListener(onMoveListener)
        binding.oP26.setOnClickListener(onMoveListener)
        binding.oP27.setOnClickListener(onMoveListener)
        binding.oP30.setOnClickListener(onMoveListener)
        binding.oP31.setOnClickListener(onMoveListener)
        binding.oP32.setOnClickListener(onMoveListener)
        binding.oP33.setOnClickListener(onMoveListener)
        binding.oP34.setOnClickListener(onMoveListener)
        binding.oP35.setOnClickListener(onMoveListener)
        binding.oP36.setOnClickListener(onMoveListener)
        binding.oP37.setOnClickListener(onMoveListener)
        binding.oP40.setOnClickListener(onMoveListener)
        binding.oP41.setOnClickListener(onMoveListener)
        binding.oP42.setOnClickListener(onMoveListener)
        binding.oP43.setOnClickListener(onMoveListener)
        binding.oP44.setOnClickListener(onMoveListener)
        binding.oP45.setOnClickListener(onMoveListener)
        binding.oP46.setOnClickListener(onMoveListener)
        binding.oP47.setOnClickListener(onMoveListener)
        binding.oP50.setOnClickListener(onMoveListener)
        binding.oP51.setOnClickListener(onMoveListener)
        binding.oP52.setOnClickListener(onMoveListener)
        binding.oP53.setOnClickListener(onMoveListener)
        binding.oP54.setOnClickListener(onMoveListener)
        binding.oP55.setOnClickListener(onMoveListener)
        binding.oP56.setOnClickListener(onMoveListener)
        binding.oP57.setOnClickListener(onMoveListener)
        binding.oP60.setOnClickListener(onMoveListener)
        binding.oP61.setOnClickListener(onMoveListener)
        binding.oP62.setOnClickListener(onMoveListener)
        binding.oP63.setOnClickListener(onMoveListener)
        binding.oP64.setOnClickListener(onMoveListener)
        binding.oP65.setOnClickListener(onMoveListener)
        binding.oP66.setOnClickListener(onMoveListener)
        binding.oP67.setOnClickListener(onMoveListener)
        binding.oP70.setOnClickListener(onMoveListener)
        binding.oP71.setOnClickListener(onMoveListener)
        binding.oP72.setOnClickListener(onMoveListener)
        binding.oP73.setOnClickListener(onMoveListener)
        binding.oP74.setOnClickListener(onMoveListener)
        binding.oP75.setOnClickListener(onMoveListener)
        binding.oP76.setOnClickListener(onMoveListener)
        binding.oP77.setOnClickListener(onMoveListener)
    }

    private fun resetBoardColor() {
        binding.oB00.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB01.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB02.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB03.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB04.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB05.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB06.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB07.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB10.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB11.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB12.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB13.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB14.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB15.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB16.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB17.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB20.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB21.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB22.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB23.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB24.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB25.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB26.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB27.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB30.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB31.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB32.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB33.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB34.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB35.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB36.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB37.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB40.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB41.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB42.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB43.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB44.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB45.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB46.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB47.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB50.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB51.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB52.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB53.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB54.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB55.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB56.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB57.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB60.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB61.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB62.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB63.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB64.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB65.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB66.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB67.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB70.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB71.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB72.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB73.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB74.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB75.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.oB76.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.oB77.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
    }

    private fun turnManager() {
        if (isValidTouch) {
            if (binding.whiteTurn.visibility == View.VISIBLE) {
                binding.whiteTurn.visibility = View.INVISIBLE
                binding.blackTurn.visibility = View.VISIBLE
            } else if (binding.whiteTurn.visibility == View.INVISIBLE) {
                binding.whiteTurn.visibility = View.VISIBLE
                binding.blackTurn.visibility = View.INVISIBLE
            }
            isWhiteTurn = !isWhiteTurn
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        moveTaskToBack(true)
        finishAndRemoveTask()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}