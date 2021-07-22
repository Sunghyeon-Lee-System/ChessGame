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
    private val board = findViewById<LinearLayout>(R.id.board)

    private val p00 = findViewById<TextView>(R.id.p00)
    private val p01 = findViewById<TextView>(R.id.p01)
    private val p02 = findViewById<TextView>(R.id.p02)
    private val p03 = findViewById<TextView>(R.id.p03)
    private val p04 = findViewById<TextView>(R.id.p04)
    private val p05 = findViewById<TextView>(R.id.p05)
    private val p06 = findViewById<TextView>(R.id.p06)
    private val p07 = findViewById<TextView>(R.id.p07)
    private val p10 = findViewById<TextView>(R.id.p10)
    private val p11 = findViewById<TextView>(R.id.p11)
    private val p12 = findViewById<TextView>(R.id.p12)
    private val p13 = findViewById<TextView>(R.id.p13)
    private val p14 = findViewById<TextView>(R.id.p14)
    private val p15 = findViewById<TextView>(R.id.p15)
    private val p16 = findViewById<TextView>(R.id.p16)
    private val p17 = findViewById<TextView>(R.id.p17)
    private val p20 = findViewById<TextView>(R.id.p20)
    private val p21 = findViewById<TextView>(R.id.p21)
    private val p22 = findViewById<TextView>(R.id.p22)
    private val p23 = findViewById<TextView>(R.id.p23)
    private val p24 = findViewById<TextView>(R.id.p24)
    private val p25 = findViewById<TextView>(R.id.p25)
    private val p26 = findViewById<TextView>(R.id.p26)
    private val p27 = findViewById<TextView>(R.id.p27)
    private val p30 = findViewById<TextView>(R.id.p30)
    private val p31 = findViewById<TextView>(R.id.p31)
    private val p32 = findViewById<TextView>(R.id.p32)
    private val p33 = findViewById<TextView>(R.id.p33)
    private val p34 = findViewById<TextView>(R.id.p34)
    private val p35 = findViewById<TextView>(R.id.p35)
    private val p36 = findViewById<TextView>(R.id.p36)
    private val p37 = findViewById<TextView>(R.id.p37)
    private val p40 = findViewById<TextView>(R.id.p40)
    private val p41 = findViewById<TextView>(R.id.p41)
    private val p42 = findViewById<TextView>(R.id.p42)
    private val p43 = findViewById<TextView>(R.id.p43)
    private val p44 = findViewById<TextView>(R.id.p44)
    private val p45 = findViewById<TextView>(R.id.p45)
    private val p46 = findViewById<TextView>(R.id.p46)
    private val p47 = findViewById<TextView>(R.id.p47)
    private val p50 = findViewById<TextView>(R.id.p50)
    private val p51 = findViewById<TextView>(R.id.p51)
    private val p52 = findViewById<TextView>(R.id.p52)
    private val p53 = findViewById<TextView>(R.id.p53)
    private val p54 = findViewById<TextView>(R.id.p54)
    private val p55 = findViewById<TextView>(R.id.p55)
    private val p56 = findViewById<TextView>(R.id.p56)
    private val p57 = findViewById<TextView>(R.id.p57)
    private val p60 = findViewById<TextView>(R.id.p60)
    private val p61 = findViewById<TextView>(R.id.p61)
    private val p62 = findViewById<TextView>(R.id.p62)
    private val p63 = findViewById<TextView>(R.id.p63)
    private val p64 = findViewById<TextView>(R.id.p64)
    private val p65 = findViewById<TextView>(R.id.p65)
    private val p66 = findViewById<TextView>(R.id.p66)
    private val p67 = findViewById<TextView>(R.id.p67)
    private val p70 = findViewById<TextView>(R.id.p70)
    private val p71 = findViewById<TextView>(R.id.p71)
    private val p72 = findViewById<TextView>(R.id.p72)
    private val p73 = findViewById<TextView>(R.id.p73)
    private val p74 = findViewById<TextView>(R.id.p74)
    private val p75 = findViewById<TextView>(R.id.p75)
    private val p76 = findViewById<TextView>(R.id.p76)
    private val p77 = findViewById<TextView>(R.id.p77)

    private val b00 = findViewById<TextView>(R.id.b00)
    private val b01 = findViewById<TextView>(R.id.b01)
    private val b02 = findViewById<TextView>(R.id.b02)
    private val b03 = findViewById<TextView>(R.id.b03)
    private val b04 = findViewById<TextView>(R.id.b04)
    private val b05 = findViewById<TextView>(R.id.b05)
    private val b06 = findViewById<TextView>(R.id.b06)
    private val b07 = findViewById<TextView>(R.id.b07)
    private val b10 = findViewById<TextView>(R.id.b10)
    private val b11 = findViewById<TextView>(R.id.b11)
    private val b12 = findViewById<TextView>(R.id.b12)
    private val b13 = findViewById<TextView>(R.id.b13)
    private val b14 = findViewById<TextView>(R.id.b14)
    private val b15 = findViewById<TextView>(R.id.b15)
    private val b16 = findViewById<TextView>(R.id.b16)
    private val b17 = findViewById<TextView>(R.id.b17)
    private val b20 = findViewById<TextView>(R.id.b20)
    private val b21 = findViewById<TextView>(R.id.b21)
    private val b22 = findViewById<TextView>(R.id.b22)
    private val b23 = findViewById<TextView>(R.id.b23)
    private val b24 = findViewById<TextView>(R.id.b24)
    private val b25 = findViewById<TextView>(R.id.b25)
    private val b26 = findViewById<TextView>(R.id.b26)
    private val b27 = findViewById<TextView>(R.id.b27)
    private val b30 = findViewById<TextView>(R.id.b30)
    private val b31 = findViewById<TextView>(R.id.b31)
    private val b32 = findViewById<TextView>(R.id.b32)
    private val b33 = findViewById<TextView>(R.id.b33)
    private val b34 = findViewById<TextView>(R.id.b34)
    private val b35 = findViewById<TextView>(R.id.b35)
    private val b36 = findViewById<TextView>(R.id.b36)
    private val b37 = findViewById<TextView>(R.id.b37)
    private val b40 = findViewById<TextView>(R.id.b40)
    private val b41 = findViewById<TextView>(R.id.b41)
    private val b42 = findViewById<TextView>(R.id.b42)
    private val b43 = findViewById<TextView>(R.id.b43)
    private val b44 = findViewById<TextView>(R.id.b44)
    private val b45 = findViewById<TextView>(R.id.b45)
    private val b46 = findViewById<TextView>(R.id.b46)
    private val b47 = findViewById<TextView>(R.id.b47)
    private val b50 = findViewById<TextView>(R.id.b50)
    private val b51 = findViewById<TextView>(R.id.b51)
    private val b52 = findViewById<TextView>(R.id.b52)
    private val b53 = findViewById<TextView>(R.id.b53)
    private val b54 = findViewById<TextView>(R.id.b54)
    private val b55 = findViewById<TextView>(R.id.b55)
    private val b56 = findViewById<TextView>(R.id.b56)
    private val b57 = findViewById<TextView>(R.id.b57)
    private val b60 = findViewById<TextView>(R.id.b60)
    private val b61 = findViewById<TextView>(R.id.b61)
    private val b62 = findViewById<TextView>(R.id.b62)
    private val b63 = findViewById<TextView>(R.id.b63)
    private val b64 = findViewById<TextView>(R.id.b64)
    private val b65 = findViewById<TextView>(R.id.b65)
    private val b66 = findViewById<TextView>(R.id.b66)
    private val b67 = findViewById<TextView>(R.id.b67)
    private val b70 = findViewById<TextView>(R.id.b70)
    private val b71 = findViewById<TextView>(R.id.b71)
    private val b72 = findViewById<TextView>(R.id.b72)
    private val b73 = findViewById<TextView>(R.id.b73)
    private val b74 = findViewById<TextView>(R.id.b74)
    private val b75 = findViewById<TextView>(R.id.b75)
    private val b76 = findViewById<TextView>(R.id.b76)
    private val b77 = findViewById<TextView>(R.id.b77)

    private val blackTurn = findViewById<LinearLayout>(R.id.blackTurn)
    private val whiteTurn = findViewById<LinearLayout>(R.id.whiteTurn)

    private val myName = findViewById<TextView>(R.id.myName)
    private val yourName = findViewById<TextView>(R.id.yourName)
    private val chatButton = findViewById<Button>(R.id.chatButton)

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
        setContentView(R.layout.activity_two_device_game)

        val intent = intent
        mMyName = intent.getStringExtra("name").toString()
        myName.text = mMyName

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

                yourName.text = mYourName
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
                                    val check_okButton = findViewById<Button>(R.id.check_okButton)
                                    val builder = AlertDialog.Builder(this@TwoDeviceGameActivity)
                                    builder.setView(checkView)
                                    val dialog = builder.create()
                                    dialog.show()
                                    dialog.window?.setLayout(650, 360)

                                    val okButton: TextView = check_okButton
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
                                    val checkmate_okButton =
                                        findViewById<Button>(R.id.checkmate_okButton)
                                    val builder = AlertDialog.Builder(this@TwoDeviceGameActivity)
                                    builder.setView(checkMateView)
                                    val dialog = builder.create()
                                    dialog.show()
                                    dialog.window?.setLayout(650, 500)

                                    val okButton: TextView = checkmate_okButton
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
                                    val stalemate_okButton =
                                        findViewById<Button>(R.id.stalemate_okButton)
                                    val builder = AlertDialog.Builder(this@TwoDeviceGameActivity)
                                    builder.setView(staleMateView)
                                    val dialog = builder.create()
                                    dialog.show()
                                    dialog.window?.setLayout(650, 500)

                                    val okButton: TextView = stalemate_okButton
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

        chatButton.setOnClickListener {
            bottomSheetDialog.show()
            chatButton.setBackgroundColor(Color.rgb(190, 190, 190))
        }
    }

    private fun setListener() {
        p00.setOnClickListener(this)
        p01.setOnClickListener(this)
        p02.setOnClickListener(this)
        p03.setOnClickListener(this)
        p04.setOnClickListener(this)
        p05.setOnClickListener(this)
        p06.setOnClickListener(this)
        p07.setOnClickListener(this)
        p10.setOnClickListener(this)
        p11.setOnClickListener(this)
        p12.setOnClickListener(this)
        p13.setOnClickListener(this)
        p14.setOnClickListener(this)
        p15.setOnClickListener(this)
        p16.setOnClickListener(this)
        p17.setOnClickListener(this)
        p20.setOnClickListener(this)
        p21.setOnClickListener(this)
        p22.setOnClickListener(this)
        p23.setOnClickListener(this)
        p24.setOnClickListener(this)
        p25.setOnClickListener(this)
        p26.setOnClickListener(this)
        p27.setOnClickListener(this)
        p30.setOnClickListener(this)
        p31.setOnClickListener(this)
        p32.setOnClickListener(this)
        p33.setOnClickListener(this)
        p34.setOnClickListener(this)
        p35.setOnClickListener(this)
        p36.setOnClickListener(this)
        p37.setOnClickListener(this)
        p40.setOnClickListener(this)
        p41.setOnClickListener(this)
        p42.setOnClickListener(this)
        p43.setOnClickListener(this)
        p44.setOnClickListener(this)
        p45.setOnClickListener(this)
        p46.setOnClickListener(this)
        p47.setOnClickListener(this)
        p50.setOnClickListener(this)
        p51.setOnClickListener(this)
        p52.setOnClickListener(this)
        p53.setOnClickListener(this)
        p54.setOnClickListener(this)
        p55.setOnClickListener(this)
        p56.setOnClickListener(this)
        p57.setOnClickListener(this)
        p60.setOnClickListener(this)
        p61.setOnClickListener(this)
        p62.setOnClickListener(this)
        p63.setOnClickListener(this)
        p64.setOnClickListener(this)
        p65.setOnClickListener(this)
        p66.setOnClickListener(this)
        p67.setOnClickListener(this)
        p70.setOnClickListener(this)
        p71.setOnClickListener(this)
        p72.setOnClickListener(this)
        p73.setOnClickListener(this)
        p74.setOnClickListener(this)
        p75.setOnClickListener(this)
        p76.setOnClickListener(this)
        p77.setOnClickListener(this)

        if (isValidTouch) {
            turnCount += 1
            DatabaseData.orderData.setValue(turnCount)
        }
    }

    private fun setMoveListener() {
        p00.setOnClickListener(onMoveListener)
        p01.setOnClickListener(onMoveListener)
        p02.setOnClickListener(onMoveListener)
        p03.setOnClickListener(onMoveListener)
        p04.setOnClickListener(onMoveListener)
        p05.setOnClickListener(onMoveListener)
        p06.setOnClickListener(onMoveListener)
        p07.setOnClickListener(onMoveListener)
        p10.setOnClickListener(onMoveListener)
        p11.setOnClickListener(onMoveListener)
        p12.setOnClickListener(onMoveListener)
        p13.setOnClickListener(onMoveListener)
        p14.setOnClickListener(onMoveListener)
        p15.setOnClickListener(onMoveListener)
        p16.setOnClickListener(onMoveListener)
        p17.setOnClickListener(onMoveListener)
        p20.setOnClickListener(onMoveListener)
        p21.setOnClickListener(onMoveListener)
        p22.setOnClickListener(onMoveListener)
        p23.setOnClickListener(onMoveListener)
        p24.setOnClickListener(onMoveListener)
        p25.setOnClickListener(onMoveListener)
        p26.setOnClickListener(onMoveListener)
        p27.setOnClickListener(onMoveListener)
        p30.setOnClickListener(onMoveListener)
        p31.setOnClickListener(onMoveListener)
        p32.setOnClickListener(onMoveListener)
        p33.setOnClickListener(onMoveListener)
        p34.setOnClickListener(onMoveListener)
        p35.setOnClickListener(onMoveListener)
        p36.setOnClickListener(onMoveListener)
        p37.setOnClickListener(onMoveListener)
        p40.setOnClickListener(onMoveListener)
        p41.setOnClickListener(onMoveListener)
        p42.setOnClickListener(onMoveListener)
        p43.setOnClickListener(onMoveListener)
        p44.setOnClickListener(onMoveListener)
        p45.setOnClickListener(onMoveListener)
        p46.setOnClickListener(onMoveListener)
        p47.setOnClickListener(onMoveListener)
        p50.setOnClickListener(onMoveListener)
        p51.setOnClickListener(onMoveListener)
        p52.setOnClickListener(onMoveListener)
        p53.setOnClickListener(onMoveListener)
        p54.setOnClickListener(onMoveListener)
        p55.setOnClickListener(onMoveListener)
        p56.setOnClickListener(onMoveListener)
        p57.setOnClickListener(onMoveListener)
        p60.setOnClickListener(onMoveListener)
        p61.setOnClickListener(onMoveListener)
        p62.setOnClickListener(onMoveListener)
        p63.setOnClickListener(onMoveListener)
        p64.setOnClickListener(onMoveListener)
        p65.setOnClickListener(onMoveListener)
        p66.setOnClickListener(onMoveListener)
        p67.setOnClickListener(onMoveListener)
        p70.setOnClickListener(onMoveListener)
        p71.setOnClickListener(onMoveListener)
        p72.setOnClickListener(onMoveListener)
        p73.setOnClickListener(onMoveListener)
        p74.setOnClickListener(onMoveListener)
        p75.setOnClickListener(onMoveListener)
        p76.setOnClickListener(onMoveListener)
        p77.setOnClickListener(onMoveListener)
    }

    private fun resetBoardColor() {
        b00.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b01.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b02.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b03.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b04.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b05.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b06.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b07.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b10.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b11.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b12.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b13.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b14.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b15.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b16.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b17.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b20.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b21.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b22.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b23.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b24.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b25.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b26.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b27.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b30.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b31.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b32.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b33.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b34.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b35.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b36.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b37.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b40.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b41.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b42.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b43.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b44.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b45.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b46.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b47.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b50.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b51.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b52.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b53.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b54.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b55.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b56.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b57.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b60.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b61.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b62.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b63.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b64.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b65.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b66.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b67.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b70.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b71.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b72.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b73.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b74.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b75.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
        b76.setBackgroundColor(resources.getColor(R.color.BLACK_BOARD))
        b77.setBackgroundColor(resources.getColor(R.color.WHITE_BOARD))
    }

    private fun push() {
        DatabaseData.boardData.setValue(ExchangeArrayAndList.arrayToList(boardPosition))
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

    private fun rotate() {
        if (!isIWhite) {
            board.rotation = 180F
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBackPressed() {
        moveTaskToBack(true)
        finishAndRemoveTask()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}