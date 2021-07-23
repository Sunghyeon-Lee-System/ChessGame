package com.example.chessgame.activities

import android.app.ProgressDialog
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.chessgame.*
import com.example.chessgame.databinding.ActivityTwoDeviceGameBinding
import com.example.chessgame.pieces.*
import com.example.chessgame.shareddata.DatabaseData
import com.example.chessgame.shareddata.MovementOfKingAndRook2Device
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.HashSet

class TwoDeviceGameActivity : AppCompatActivity(), View.OnClickListener {

    private var mBinding: ActivityTwoDeviceGameBinding? = null
    private val binding get() = mBinding!!

    private lateinit var clickedTilePosition: Position

    private var isMyTurn = true
    private var isIWhite = false
    private var turnCount = -1L

    private var isFirstCall_name = true
    private var isFirstCall_order = true
    private var isFirstCall_check = true

    private var isValidTouch = true
    private var isWhiteTurn = true
    private var killOneSelf = false
    private var isCheck = false

    private var canEnpassant = false
    private val enpassantPosition = HashSet<Position>()
    private var enpassantPawnPosition = Position(-1, -1)

    private lateinit var mMyName: String
    private var mYourName = "yourName"
    private lateinit var progressDialog: ProgressDialog

    private var boardPosition = Array(8) {
        Array<Piece>(8) {
            Empty()
        }
    }

    private val onMoveListener = View.OnClickListener {
        val intId = it.id
        val id = resources.getResourceEntryName(intId)
        val x = Integer.parseInt(id[1].toString())
        val y = Integer.parseInt(id[2].toString())

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
                    MovementOfKingAndRook2Device.isWhiteKingMoved = true
                } else {
                    MovementOfKingAndRook2Device.isBlackKingMoved = true
                }
            } else if (clickedTileType == "Rook") {
                if (y == 0) {
                    if (clickedTileColor) {
                        MovementOfKingAndRook2Device.isWhiteLeftRookMoved = true
                    } else {
                        MovementOfKingAndRook2Device.isBlackLeftRookMoved = true
                    }
                } else if (y == 7) {
                    if (clickedTileColor) {
                        MovementOfKingAndRook2Device.isWhiteRightRookMoved = true
                    } else {
                        MovementOfKingAndRook2Device.isBlackRightRookMoved = true
                    }
                }
            }

            if (MovementOfKingAndRook2Device.whiteKSC) {
                if (x == 7 && y == 6) {
                    boardPosition[7][7] = Empty()
                    boardPosition[7][5] = Rook(true)
                }
                MovementOfKingAndRook2Device.whiteKSC = false
            }
            if (MovementOfKingAndRook2Device.whiteQSC) {
                if (x == 7 && y == 2) {
                    boardPosition[7][0] = Empty()
                    boardPosition[7][3] = Rook(true)
                }
                MovementOfKingAndRook2Device.whiteQSC = false
            }
            if (MovementOfKingAndRook2Device.blackKSC) {
                if (x == 0 && y == 6) {
                    boardPosition[0][7] = Empty()
                    boardPosition[0][5] = Rook(false)
                }
                MovementOfKingAndRook2Device.blackKSC = false
            }
            if (MovementOfKingAndRook2Device.blackQSC) {
                if (x == 0 && y == 2) {
                    boardPosition[0][0] = Empty()
                    boardPosition[0][3] = Rook(false)
                }
                MovementOfKingAndRook2Device.blackQSC = false
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
                            enpassantPosition.add(Position(first.x - 1, first.y))
                            enpassantPawnPosition = second
                        }
                    } else if (first.x == 1) {
                        if (x == first.x + 2 && y == first.y) {
                            canEnpassant = true
                            enpassantPosition.add(Position(first.x + 1, first.y))
                            enpassantPawnPosition = second
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

            val wKingPos = DetailedRules(boardPosition).getKingPosition(true)
            val bKingPos = DetailedRules(boardPosition).getKingPosition(false)

            if (isIWhite) {
                if (DetailedRules(boardPosition).isCheck(wKingPos.x, wKingPos.y, true)) {
                    killOneSelf = true
                }
            } else {
                if (DetailedRules(boardPosition).isCheck(bKingPos.x, bKingPos.y, false)) {
                    killOneSelf = true
                }
            }

            if (killOneSelf) {
                if (x != clickedTileX || y != clickedTileY) {
                    val outMetrix = DisplayMetrics()
                    windowManager.defaultDisplay.getMetrics(outMetrix)
                    val cannotMoveView =
                        View.inflate(
                            this@TwoDeviceGameActivity,
                            R.layout.toast_cannotmove,
                            null
                        )
                    val toast = Toast(this@TwoDeviceGameActivity)
                    toast.view = cannotMoveView
                    toast.setGravity(Gravity.CENTER, 0, 320 * outMetrix.density.toInt())
                    toast.duration = Toast.LENGTH_SHORT
                    toast.show()
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

            if (!killOneSelf && isCheck) {
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
                        val cannotMoveView =
                            View.inflate(
                                this@TwoDeviceGameActivity,
                                R.layout.toast_cannotmove,
                                null
                            )
                        val toast = Toast(this@TwoDeviceGameActivity)
                        toast.view = cannotMoveView
                        toast.setGravity(Gravity.CENTER, 0, 320 * outMetrix.density.toInt())
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
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

            val detailedRules = DetailedRules(boardPosition)

            val opposingKingPos = detailedRules.getKingPosition(!clickedTileColor)

            val isKingInDanger =
                detailedRules.isCheck(opposingKingPos.x, opposingKingPos.y, !clickedTileColor)
            if (!killOneSelf && !isCheck) {
                if (isKingInDanger) {
                    if (detailedRules.isCheckMate(clickedTileColor)) {
                        DatabaseData.checkData.setValue(Pair(mMyName, "checkmate"))
                    } else {
                        DatabaseData.checkData.setValue(Pair(mMyName, "check $turnCount"))
                    }
                } else {
                    if (DetailedRules(boardPosition).isStaleMate(!clickedTileColor)) {
                        DatabaseData.checkData.setValue(Pair(mMyName, "stalemate"))
                    }
                }
            }

            killOneSelf = false

            pawnPromotion()

            updateUi()
            push()
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
        mBinding = ActivityTwoDeviceGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        mMyName = intent.getStringExtra("name").toString()
        binding.myName.text = mMyName

        DatabaseData.userData.setValue(mMyName)

        DatabaseData.firstOrderData.setValue(Pair(mMyName, Random().nextBoolean()))

        boardInitialize()
        setListener()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("다른 사용자의 접속을 기다리는 중입니다...")
        progressDialog.setCancelable(false)
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal)
        progressDialog.show()
    }

    override fun onStart() {
        super.onStart()

        DatabaseData.boardData.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value
                boardPosition =
                    ExchangeArrayAndList.listToPiece(value as ArrayList<HashMap<String, Any>>)
                updateUi()
                isMyTurn = !isMyTurn
            }
        })

        DatabaseData.userData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value as String

                if (value != mMyName) {
                    mYourName = value
                    if (progressDialog.isShowing) {
                        progressDialog.dismiss()
                        DatabaseData.chatData.removeValue()
                        setChatListener()
                    }
                    if (isFirstCall_name) {
                        DatabaseData.userData.setValue(mMyName)
                        isFirstCall_name = false
                    }
                }

                binding.yourName.text = mYourName
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        DatabaseData.firstOrderData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.value as HashMap<String, Any>

                val name = value["first"]
                val order = value["second"]

                if (name != mMyName) {
                    isIWhite = (order as Boolean)
                    if (isFirstCall_order) {
                        isFirstCall_order = false
                        DatabaseData.firstOrderData.setValue(Pair(mMyName, !order))
                    }
                    rotate()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        DatabaseData.orderData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                turnCount = snapshot.value as Long
                turnManager()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        DatabaseData.checkData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    if (isFirstCall_check) {
                        isFirstCall_check = false
                    } else {
                        val check = snapshot.value as HashMap<String, String>
                        val checkName = check["first"]
                        val second = check["second"]
                        val checkSort = second?.split(" ")

                        if (checkName != mMyName) {
                            when (checkSort?.get(0)) {
                                "check" -> {
                                    isCheck = true
                                    val checkView =
                                        View.inflate(
                                            this@TwoDeviceGameActivity,
                                            R.layout.check_dialog,
                                            null
                                        )
                                    val builder = AlertDialog.Builder(this@TwoDeviceGameActivity)
                                    builder.setView(checkView)
                                    val dialog = builder.create()
                                    dialog.show()
                                    dialog.window?.setLayout(650, 360)

                                    val okButton =
                                        checkView.findViewById<Button>(R.id.check_okButton)
                                    okButton.setOnClickListener {
                                        dialog.dismiss()
                                    }
                                }
                                "checkmate" -> {
                                    val checkMateView =
                                        View.inflate(
                                            this@TwoDeviceGameActivity,
                                            R.layout.checkmate_dialog,
                                            null
                                        )
                                    val builder = AlertDialog.Builder(this@TwoDeviceGameActivity)
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
                                "stalemate" -> {
                                    val staleMateView =
                                        View.inflate(
                                            this@TwoDeviceGameActivity,
                                            R.layout.stalemate_dialog,
                                            null
                                        )
                                    val builder = AlertDialog.Builder(this@TwoDeviceGameActivity)
                                    builder.setView(staleMateView)
                                    val dialog = builder.create()
                                    dialog.show()
                                    dialog.window?.setLayout(650, 500)

                                    val okButton =
                                        staleMateView.findViewById<Button>(R.id.stalemate_okButton)
                                    okButton.setOnClickListener {
                                        dialog.dismiss()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
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
        push()
    }

    private fun updateUi() {
        for (i in 0..7) {
            for (j in 0..7) {
                val pieceKind = pieceKind(boardPosition[i][j])
                val textViewId: Int = resources.getIdentifier("p$i$j", "id", packageName)
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

    override fun onClick(v: View?) {
        var canEatPosition = HashSet<Position>()
        val intId = v?.id
        val id = intId?.let { resources.getResourceEntryName(it) }
        android.util.Log.d("ChessGame", id.toString())
        val x = Integer.parseInt(id?.get(1).toString())
        val y = Integer.parseInt(id?.get(2).toString())

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
                (boardPosition[x][y] as Pawn).getCanMoveArea(position, boardPosition, isIWhite)
            "Rook" -> canMovePositions =
                (boardPosition[x][y] as Rook).getCanMoveArea(position, boardPosition, isIWhite)
            "Knight" -> canMovePositions =
                (boardPosition[x][y] as Knight).getCanMoveArea(position, boardPosition, isIWhite)
            "Bishop" -> canMovePositions =
                (boardPosition[x][y] as Bishop).getCanMoveArea(position, boardPosition, isIWhite)
            "Queen" -> canMovePositions =
                (boardPosition[x][y] as Queen).getCanMoveArea(position, boardPosition, isIWhite)
            "King" -> canMovePositions =
                (boardPosition[x][y] as King).getCanMoveArea(position, boardPosition, isIWhite)
            "Empty" -> canMovePositions =
                (boardPosition[x][y] as Empty).getCanMoveArea(position, boardPosition)
        }
        if (isIWhite) {
            if (turnCount.toInt() % 2 != 0) {
                canMovePositions.clear()
            }
        } else {
            if (turnCount.toInt() % 2 != 1) {
                canMovePositions.clear()
            }
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
            val textViewId = resources.getIdentifier("b$x$y", "id", packageName)
            android.util.Log.d("ChessGame", "b$x$y")
            val textView: TextView = findViewById(textViewId)
            textView.setBackgroundColor(resources.getColor(R.color.CANGO_BOARD))
            boardPosition[x][y].onCanMove = true
        }

        when (pieceKind(boardPosition[x][y])) {
            "Pawn" -> canEatPosition =
                (boardPosition[x][y] as Pawn).isCanEat(position, boardPosition, isIWhite)
            "Rook" -> canEatPosition =
                (boardPosition[x][y] as Rook).isCanEat(position, boardPosition, isIWhite)
            "Knight" -> canEatPosition =
                (boardPosition[x][y] as Knight).isCanEat(position, boardPosition, isIWhite)
            "Bishop" -> canEatPosition =
                (boardPosition[x][y] as Bishop).isCanEat(position, boardPosition, isIWhite)
            "Queen" -> canEatPosition =
                (boardPosition[x][y] as Queen).isCanEat(position, boardPosition, isIWhite)
            "King" -> canEatPosition =
                (boardPosition[x][y] as King).isCanEat(position, boardPosition, isIWhite)
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
            val id = resources.getIdentifier("b$x$y", "id", packageName)
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
                push()
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
                push()
            }
        })
        dialog.show()
    }

    private fun setChatListener() {
        val inflater =
            getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.bottom_sheet_chat, null, false)
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(view)

        val chat = com.example.chessgame.ChatManager(
            this@TwoDeviceGameActivity,
            mMyName,
            bottomSheetDialog,
            DatabaseData.chatData
        )

        binding.chatButton.setOnClickListener {
            bottomSheetDialog.show()
            binding.chatButton.setBackgroundColor(Color.rgb(190, 190, 190))
        }
    }

    private fun setListener() {
        binding.p00.setOnClickListener(this)
        binding.p01.setOnClickListener(this)
        binding.p02.setOnClickListener(this)
        binding.p03.setOnClickListener(this)
        binding.p04.setOnClickListener(this)
        binding.p05.setOnClickListener(this)
        binding.p06.setOnClickListener(this)
        binding.p07.setOnClickListener(this)
        binding.p10.setOnClickListener(this)
        binding.p11.setOnClickListener(this)
        binding.p12.setOnClickListener(this)
        binding.p13.setOnClickListener(this)
        binding.p14.setOnClickListener(this)
        binding.p15.setOnClickListener(this)
        binding.p16.setOnClickListener(this)
        binding.p17.setOnClickListener(this)
        binding.p20.setOnClickListener(this)
        binding.p21.setOnClickListener(this)
        binding.p22.setOnClickListener(this)
        binding.p23.setOnClickListener(this)
        binding.p24.setOnClickListener(this)
        binding.p25.setOnClickListener(this)
        binding.p26.setOnClickListener(this)
        binding.p27.setOnClickListener(this)
        binding.p30.setOnClickListener(this)
        binding.p31.setOnClickListener(this)
        binding.p32.setOnClickListener(this)
        binding.p33.setOnClickListener(this)
        binding.p34.setOnClickListener(this)
        binding.p35.setOnClickListener(this)
        binding.p36.setOnClickListener(this)
        binding.p37.setOnClickListener(this)
        binding.p40.setOnClickListener(this)
        binding.p41.setOnClickListener(this)
        binding.p42.setOnClickListener(this)
        binding.p43.setOnClickListener(this)
        binding.p44.setOnClickListener(this)
        binding.p45.setOnClickListener(this)
        binding.p46.setOnClickListener(this)
        binding.p47.setOnClickListener(this)
        binding.p50.setOnClickListener(this)
        binding.p51.setOnClickListener(this)
        binding.p52.setOnClickListener(this)
        binding.p53.setOnClickListener(this)
        binding.p54.setOnClickListener(this)
        binding.p55.setOnClickListener(this)
        binding.p56.setOnClickListener(this)
        binding.p57.setOnClickListener(this)
        binding.p60.setOnClickListener(this)
        binding.p61.setOnClickListener(this)
        binding.p62.setOnClickListener(this)
        binding.p63.setOnClickListener(this)
        binding.p64.setOnClickListener(this)
        binding.p65.setOnClickListener(this)
        binding.p66.setOnClickListener(this)
        binding.p67.setOnClickListener(this)
        binding.p70.setOnClickListener(this)
        binding.p71.setOnClickListener(this)
        binding.p72.setOnClickListener(this)
        binding.p73.setOnClickListener(this)
        binding.p74.setOnClickListener(this)
        binding.p75.setOnClickListener(this)
        binding.p76.setOnClickListener(this)
        binding.p77.setOnClickListener(this)

        if (isValidTouch) {
            turnCount += 1
            DatabaseData.orderData.setValue(turnCount)
        }
    }

    private fun setMoveListener() {
        binding.p00.setOnClickListener(onMoveListener)
        binding.p01.setOnClickListener(onMoveListener)
        binding.p02.setOnClickListener(onMoveListener)
        binding.p03.setOnClickListener(onMoveListener)
        binding.p04.setOnClickListener(onMoveListener)
        binding.p05.setOnClickListener(onMoveListener)
        binding.p06.setOnClickListener(onMoveListener)
        binding.p07.setOnClickListener(onMoveListener)
        binding.p10.setOnClickListener(onMoveListener)
        binding.p11.setOnClickListener(onMoveListener)
        binding.p12.setOnClickListener(onMoveListener)
        binding.p13.setOnClickListener(onMoveListener)
        binding.p14.setOnClickListener(onMoveListener)
        binding.p15.setOnClickListener(onMoveListener)
        binding.p16.setOnClickListener(onMoveListener)
        binding.p17.setOnClickListener(onMoveListener)
        binding.p20.setOnClickListener(onMoveListener)
        binding.p21.setOnClickListener(onMoveListener)
        binding.p22.setOnClickListener(onMoveListener)
        binding.p23.setOnClickListener(onMoveListener)
        binding.p24.setOnClickListener(onMoveListener)
        binding.p25.setOnClickListener(onMoveListener)
        binding.p26.setOnClickListener(onMoveListener)
        binding.p27.setOnClickListener(onMoveListener)
        binding.p30.setOnClickListener(onMoveListener)
        binding.p31.setOnClickListener(onMoveListener)
        binding.p32.setOnClickListener(onMoveListener)
        binding.p33.setOnClickListener(onMoveListener)
        binding.p34.setOnClickListener(onMoveListener)
        binding.p35.setOnClickListener(onMoveListener)
        binding.p36.setOnClickListener(onMoveListener)
        binding.p37.setOnClickListener(onMoveListener)
        binding.p40.setOnClickListener(onMoveListener)
        binding.p41.setOnClickListener(onMoveListener)
        binding.p42.setOnClickListener(onMoveListener)
        binding.p43.setOnClickListener(onMoveListener)
        binding.p44.setOnClickListener(onMoveListener)
        binding.p45.setOnClickListener(onMoveListener)
        binding.p46.setOnClickListener(onMoveListener)
        binding.p47.setOnClickListener(onMoveListener)
        binding.p50.setOnClickListener(onMoveListener)
        binding.p51.setOnClickListener(onMoveListener)
        binding.p52.setOnClickListener(onMoveListener)
        binding.p53.setOnClickListener(onMoveListener)
        binding.p54.setOnClickListener(onMoveListener)
        binding.p55.setOnClickListener(onMoveListener)
        binding.p56.setOnClickListener(onMoveListener)
        binding.p57.setOnClickListener(onMoveListener)
        binding.p60.setOnClickListener(onMoveListener)
        binding.p61.setOnClickListener(onMoveListener)
        binding.p62.setOnClickListener(onMoveListener)
        binding.p63.setOnClickListener(onMoveListener)
        binding.p64.setOnClickListener(onMoveListener)
        binding.p65.setOnClickListener(onMoveListener)
        binding.p66.setOnClickListener(onMoveListener)
        binding.p67.setOnClickListener(onMoveListener)
        binding.p70.setOnClickListener(onMoveListener)
        binding.p71.setOnClickListener(onMoveListener)
        binding.p72.setOnClickListener(onMoveListener)
        binding.p73.setOnClickListener(onMoveListener)
        binding.p74.setOnClickListener(onMoveListener)
        binding.p75.setOnClickListener(onMoveListener)
        binding.p76.setOnClickListener(onMoveListener)
        binding.p77.setOnClickListener(onMoveListener)
    }

    private fun resetBoardColor() {
        binding.b00.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b01.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b02.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b03.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b04.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b05.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b06.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b07.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b10.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b11.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b12.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b13.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b14.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b15.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b16.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b17.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b20.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b21.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b22.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b23.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b24.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b25.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b26.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b27.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b30.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b31.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b32.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b33.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b34.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b35.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b36.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b37.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b40.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b41.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b42.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b43.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b44.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b45.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b46.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b47.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b50.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b51.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b52.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b53.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b54.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b55.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b56.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b57.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b60.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b61.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b62.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b63.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b64.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b65.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b66.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b67.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b70.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b71.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b72.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b73.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b74.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b75.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        binding.b76.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        binding.b77.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
    }

    private fun push() {
        DatabaseData.boardData.setValue(ExchangeArrayAndList.arrayToList(boardPosition))
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

    private fun rotate() {
        if (!isIWhite) {
            binding.board.rotation = 180F
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        moveTaskToBack(true)
        finishAndRemoveTask()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}