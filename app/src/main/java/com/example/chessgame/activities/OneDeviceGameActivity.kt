package com.example.chessgame.activities

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chessgame.*
import com.example.chessgame.pieces.*
import com.example.chessgame.shareddata.MovementOfKingAndRook1Device
import kotlinx.android.synthetic.main.activity_one_device_game.*
import kotlinx.android.synthetic.main.check_dialog.view.*
import kotlinx.android.synthetic.main.checkmate_dialog.view.*
import kotlinx.android.synthetic.main.stalemate_dialog.view.*

class OneDeviceGameActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var clickedTilePosition: Position

    private var isValidTouch = true
    private var isWhiteTurn = false
    private var canEnpassant = false
    private val enpassantPosition = HashSet<Position>()
    private var enpassantPawnPosition = Position(-1, -1)

    private var boardPosition = Array(8) {
        Array<Piece>(8) {
            Empty()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private val onMoveListener = View.OnClickListener {
        val intId = it.id
        val id = resources.getResourceEntryName(intId)
        val x = Integer.parseInt(id[3].toString())
        val y = Integer.parseInt(id[4].toString())

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

            val detailedRules = DetailedRules(boardPosition)

            val isKingInDanger =
                detailedRules.isKingInDanger(x, y, clickedTileType, clickedTileColor)
            if (isKingInDanger) {
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

                        val okButton: TextView = checkMateView.checkmate_okButton
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

                        val okButton: TextView = checkMateView.checkmate_okButton
                        okButton.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                } else {
                    if (!clickedTileColor) {
                        val checkView =
                            View.inflate(this@OneDeviceGameActivity, R.layout.check_dialog, null)
                        val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                        builder.setView(checkView)
                        val dialog = builder.create()
                        dialog.show()
                        dialog.window?.setLayout(650, 360)

                        val okButton: TextView = checkView.check_okButton
                        okButton.setOnClickListener {
                            dialog.dismiss()
                        }
                    } else {
                        val checkView =
                            View.inflate(this@OneDeviceGameActivity, R.layout.check_dialog, null)
                        checkView.rotation = 180F
                        val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                        builder.setView(checkView)
                        val dialog = builder.create()
                        dialog.show()
                        dialog.window?.setLayout(650, 360)

                        val okButton: TextView = checkView.check_okButton
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

                        val okButton: TextView = staleMateView.check_okButton
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

                        val okButton: TextView = staleMateView.stalemate_okButton
                        okButton.setOnClickListener {
                            dialog.dismiss()
                        }
                    }
                }
            }

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
        setContentView(R.layout.activity_one_device_game)

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
                val textViewId: Int = resources.getIdentifier("o_p$i$j", "id", packageName)
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
        val x = Integer.parseInt(id?.get(3).toString())
        val y = Integer.parseInt(id?.get(4).toString())

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
                        if (isWhiteTurn && y == 7) {
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
            val textViewId = resources.getIdentifier("o_b$x$y", "id", packageName)
            android.util.Log.d("ChessGame", "o_b$x$y")
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
            val id = resources.getIdentifier("o_b$x$y", "id", packageName)
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
        o_p00.setOnClickListener(this)
        o_p01.setOnClickListener(this)
        o_p02.setOnClickListener(this)
        o_p03.setOnClickListener(this)
        o_p04.setOnClickListener(this)
        o_p05.setOnClickListener(this)
        o_p06.setOnClickListener(this)
        o_p07.setOnClickListener(this)
        o_p10.setOnClickListener(this)
        o_p11.setOnClickListener(this)
        o_p12.setOnClickListener(this)
        o_p13.setOnClickListener(this)
        o_p14.setOnClickListener(this)
        o_p15.setOnClickListener(this)
        o_p16.setOnClickListener(this)
        o_p17.setOnClickListener(this)
        o_p20.setOnClickListener(this)
        o_p21.setOnClickListener(this)
        o_p22.setOnClickListener(this)
        o_p23.setOnClickListener(this)
        o_p24.setOnClickListener(this)
        o_p25.setOnClickListener(this)
        o_p26.setOnClickListener(this)
        o_p27.setOnClickListener(this)
        o_p30.setOnClickListener(this)
        o_p31.setOnClickListener(this)
        o_p32.setOnClickListener(this)
        o_p33.setOnClickListener(this)
        o_p34.setOnClickListener(this)
        o_p35.setOnClickListener(this)
        o_p36.setOnClickListener(this)
        o_p37.setOnClickListener(this)
        o_p40.setOnClickListener(this)
        o_p41.setOnClickListener(this)
        o_p42.setOnClickListener(this)
        o_p43.setOnClickListener(this)
        o_p44.setOnClickListener(this)
        o_p45.setOnClickListener(this)
        o_p46.setOnClickListener(this)
        o_p47.setOnClickListener(this)
        o_p50.setOnClickListener(this)
        o_p51.setOnClickListener(this)
        o_p52.setOnClickListener(this)
        o_p53.setOnClickListener(this)
        o_p54.setOnClickListener(this)
        o_p55.setOnClickListener(this)
        o_p56.setOnClickListener(this)
        o_p57.setOnClickListener(this)
        o_p60.setOnClickListener(this)
        o_p61.setOnClickListener(this)
        o_p62.setOnClickListener(this)
        o_p63.setOnClickListener(this)
        o_p64.setOnClickListener(this)
        o_p65.setOnClickListener(this)
        o_p66.setOnClickListener(this)
        o_p67.setOnClickListener(this)
        o_p70.setOnClickListener(this)
        o_p71.setOnClickListener(this)
        o_p72.setOnClickListener(this)
        o_p73.setOnClickListener(this)
        o_p74.setOnClickListener(this)
        o_p75.setOnClickListener(this)
        o_p76.setOnClickListener(this)
        o_p77.setOnClickListener(this)

        turnManager()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setMoveListener() {
        o_p00.setOnClickListener(onMoveListener)
        o_p01.setOnClickListener(onMoveListener)
        o_p02.setOnClickListener(onMoveListener)
        o_p03.setOnClickListener(onMoveListener)
        o_p04.setOnClickListener(onMoveListener)
        o_p05.setOnClickListener(onMoveListener)
        o_p06.setOnClickListener(onMoveListener)
        o_p07.setOnClickListener(onMoveListener)
        o_p10.setOnClickListener(onMoveListener)
        o_p11.setOnClickListener(onMoveListener)
        o_p12.setOnClickListener(onMoveListener)
        o_p13.setOnClickListener(onMoveListener)
        o_p14.setOnClickListener(onMoveListener)
        o_p15.setOnClickListener(onMoveListener)
        o_p16.setOnClickListener(onMoveListener)
        o_p17.setOnClickListener(onMoveListener)
        o_p20.setOnClickListener(onMoveListener)
        o_p21.setOnClickListener(onMoveListener)
        o_p22.setOnClickListener(onMoveListener)
        o_p23.setOnClickListener(onMoveListener)
        o_p24.setOnClickListener(onMoveListener)
        o_p25.setOnClickListener(onMoveListener)
        o_p26.setOnClickListener(onMoveListener)
        o_p27.setOnClickListener(onMoveListener)
        o_p30.setOnClickListener(onMoveListener)
        o_p31.setOnClickListener(onMoveListener)
        o_p32.setOnClickListener(onMoveListener)
        o_p33.setOnClickListener(onMoveListener)
        o_p34.setOnClickListener(onMoveListener)
        o_p35.setOnClickListener(onMoveListener)
        o_p36.setOnClickListener(onMoveListener)
        o_p37.setOnClickListener(onMoveListener)
        o_p40.setOnClickListener(onMoveListener)
        o_p41.setOnClickListener(onMoveListener)
        o_p42.setOnClickListener(onMoveListener)
        o_p43.setOnClickListener(onMoveListener)
        o_p44.setOnClickListener(onMoveListener)
        o_p45.setOnClickListener(onMoveListener)
        o_p46.setOnClickListener(onMoveListener)
        o_p47.setOnClickListener(onMoveListener)
        o_p50.setOnClickListener(onMoveListener)
        o_p51.setOnClickListener(onMoveListener)
        o_p52.setOnClickListener(onMoveListener)
        o_p53.setOnClickListener(onMoveListener)
        o_p54.setOnClickListener(onMoveListener)
        o_p55.setOnClickListener(onMoveListener)
        o_p56.setOnClickListener(onMoveListener)
        o_p57.setOnClickListener(onMoveListener)
        o_p60.setOnClickListener(onMoveListener)
        o_p61.setOnClickListener(onMoveListener)
        o_p62.setOnClickListener(onMoveListener)
        o_p63.setOnClickListener(onMoveListener)
        o_p64.setOnClickListener(onMoveListener)
        o_p65.setOnClickListener(onMoveListener)
        o_p66.setOnClickListener(onMoveListener)
        o_p67.setOnClickListener(onMoveListener)
        o_p70.setOnClickListener(onMoveListener)
        o_p71.setOnClickListener(onMoveListener)
        o_p72.setOnClickListener(onMoveListener)
        o_p73.setOnClickListener(onMoveListener)
        o_p74.setOnClickListener(onMoveListener)
        o_p75.setOnClickListener(onMoveListener)
        o_p76.setOnClickListener(onMoveListener)
        o_p77.setOnClickListener(onMoveListener)
    }

    private fun resetBoardColor() {
        o_b00.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b01.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b02.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b03.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b04.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b05.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b06.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b07.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b10.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b11.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b12.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b13.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b14.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b15.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b16.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b17.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b20.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b21.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b22.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b23.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b24.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b25.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b26.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b27.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b30.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b31.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b32.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b33.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b34.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b35.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b36.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b37.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b40.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b41.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b42.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b43.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b44.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b45.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b46.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b47.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b50.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b51.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b52.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b53.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b54.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b55.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b56.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b57.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b60.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b61.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b62.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b63.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b64.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b65.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b66.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b67.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b70.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b71.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b72.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b73.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b74.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b75.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        o_b76.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        o_b77.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
    }

    private fun turnManager() {
        if (isValidTouch) {
            if (whiteTurn.visibility == View.VISIBLE) {
                whiteTurn.visibility = View.INVISIBLE
                blackTurn.visibility = View.VISIBLE
            } else if (whiteTurn.visibility == View.INVISIBLE) {
                whiteTurn.visibility = View.VISIBLE
                blackTurn.visibility = View.INVISIBLE
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