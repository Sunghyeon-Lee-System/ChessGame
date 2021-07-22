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
import com.example.chessgame.pieces.*
import com.example.chessgame.shareddata.MovementOfKingAndRook1Device

class OneDeviceGameActivity : AppCompatActivity(), View.OnClickListener {
    private val o_p00 = findViewById<TextView>(R.id.o_p00)
    private val o_p01 = findViewById<TextView>(R.id.o_p01)
    private val o_p02 = findViewById<TextView>(R.id.o_p02)
    private val o_p03 = findViewById<TextView>(R.id.o_p03)
    private val o_p04 = findViewById<TextView>(R.id.o_p04)
    private val o_p05 = findViewById<TextView>(R.id.o_p05)
    private val o_p06 = findViewById<TextView>(R.id.o_p06)
    private val o_p07 = findViewById<TextView>(R.id.o_p07)
    private val o_p10 = findViewById<TextView>(R.id.o_p10)
    private val o_p11 = findViewById<TextView>(R.id.o_p11)
    private val o_p12 = findViewById<TextView>(R.id.o_p12)
    private val o_p13 = findViewById<TextView>(R.id.o_p13)
    private val o_p14 = findViewById<TextView>(R.id.o_p14)
    private val o_p15 = findViewById<TextView>(R.id.o_p15)
    private val o_p16 = findViewById<TextView>(R.id.o_p16)
    private val o_p17 = findViewById<TextView>(R.id.o_p17)
    private val o_p20 = findViewById<TextView>(R.id.o_p20)
    private val o_p21 = findViewById<TextView>(R.id.o_p21)
    private val o_p22 = findViewById<TextView>(R.id.o_p22)
    private val o_p23 = findViewById<TextView>(R.id.o_p23)
    private val o_p24 = findViewById<TextView>(R.id.o_p24)
    private val o_p25 = findViewById<TextView>(R.id.o_p25)
    private val o_p26 = findViewById<TextView>(R.id.o_p26)
    private val o_p27 = findViewById<TextView>(R.id.o_p27)
    private val o_p30 = findViewById<TextView>(R.id.o_p30)
    private val o_p31 = findViewById<TextView>(R.id.o_p31)
    private val o_p32 = findViewById<TextView>(R.id.o_p32)
    private val o_p33 = findViewById<TextView>(R.id.o_p33)
    private val o_p34 = findViewById<TextView>(R.id.o_p34)
    private val o_p35 = findViewById<TextView>(R.id.o_p35)
    private val o_p36 = findViewById<TextView>(R.id.o_p36)
    private val o_p37 = findViewById<TextView>(R.id.o_p37)
    private val o_p40 = findViewById<TextView>(R.id.o_p40)
    private val o_p41 = findViewById<TextView>(R.id.o_p41)
    private val o_p42 = findViewById<TextView>(R.id.o_p42)
    private val o_p43 = findViewById<TextView>(R.id.o_p43)
    private val o_p44 = findViewById<TextView>(R.id.o_p44)
    private val o_p45 = findViewById<TextView>(R.id.o_p45)
    private val o_p46 = findViewById<TextView>(R.id.o_p46)
    private val o_p47 = findViewById<TextView>(R.id.o_p47)
    private val o_p50 = findViewById<TextView>(R.id.o_p50)
    private val o_p51 = findViewById<TextView>(R.id.o_p51)
    private val o_p52 = findViewById<TextView>(R.id.o_p52)
    private val o_p53 = findViewById<TextView>(R.id.o_p53)
    private val o_p54 = findViewById<TextView>(R.id.o_p54)
    private val o_p55 = findViewById<TextView>(R.id.o_p55)
    private val o_p56 = findViewById<TextView>(R.id.o_p56)
    private val o_p57 = findViewById<TextView>(R.id.o_p57)
    private val o_p60 = findViewById<TextView>(R.id.o_p60)
    private val o_p61 = findViewById<TextView>(R.id.o_p61)
    private val o_p62 = findViewById<TextView>(R.id.o_p62)
    private val o_p63 = findViewById<TextView>(R.id.o_p63)
    private val o_p64 = findViewById<TextView>(R.id.o_p64)
    private val o_p65 = findViewById<TextView>(R.id.o_p65)
    private val o_p66 = findViewById<TextView>(R.id.o_p66)
    private val o_p67 = findViewById<TextView>(R.id.o_p67)
    private val o_p70 = findViewById<TextView>(R.id.o_p70)
    private val o_p71 = findViewById<TextView>(R.id.o_p71)
    private val o_p72 = findViewById<TextView>(R.id.o_p72)
    private val o_p73 = findViewById<TextView>(R.id.o_p73)
    private val o_p74 = findViewById<TextView>(R.id.o_p74)
    private val o_p75 = findViewById<TextView>(R.id.o_p75)
    private val o_p76 = findViewById<TextView>(R.id.o_p76)
    private val o_p77 = findViewById<TextView>(R.id.o_p77)

    private val o_b00 = findViewById<TextView>(R.id.o_b00)
    private val o_b01 = findViewById<TextView>(R.id.o_b01)
    private val o_b02 = findViewById<TextView>(R.id.o_b02)
    private val o_b03 = findViewById<TextView>(R.id.o_b03)
    private val o_b04 = findViewById<TextView>(R.id.o_b04)
    private val o_b05 = findViewById<TextView>(R.id.o_b05)
    private val o_b06 = findViewById<TextView>(R.id.o_b06)
    private val o_b07 = findViewById<TextView>(R.id.o_b07)
    private val o_b10 = findViewById<TextView>(R.id.o_b10)
    private val o_b11 = findViewById<TextView>(R.id.o_b11)
    private val o_b12 = findViewById<TextView>(R.id.o_b12)
    private val o_b13 = findViewById<TextView>(R.id.o_b13)
    private val o_b14 = findViewById<TextView>(R.id.o_b14)
    private val o_b15 = findViewById<TextView>(R.id.o_b15)
    private val o_b16 = findViewById<TextView>(R.id.o_b16)
    private val o_b17 = findViewById<TextView>(R.id.o_b17)
    private val o_b20 = findViewById<TextView>(R.id.o_b20)
    private val o_b21 = findViewById<TextView>(R.id.o_b21)
    private val o_b22 = findViewById<TextView>(R.id.o_b22)
    private val o_b23 = findViewById<TextView>(R.id.o_b23)
    private val o_b24 = findViewById<TextView>(R.id.o_b24)
    private val o_b25 = findViewById<TextView>(R.id.o_b25)
    private val o_b26 = findViewById<TextView>(R.id.o_b26)
    private val o_b27 = findViewById<TextView>(R.id.o_b27)
    private val o_b30 = findViewById<TextView>(R.id.o_b30)
    private val o_b31 = findViewById<TextView>(R.id.o_b31)
    private val o_b32 = findViewById<TextView>(R.id.o_b32)
    private val o_b33 = findViewById<TextView>(R.id.o_b33)
    private val o_b34 = findViewById<TextView>(R.id.o_b34)
    private val o_b35 = findViewById<TextView>(R.id.o_b35)
    private val o_b36 = findViewById<TextView>(R.id.o_b36)
    private val o_b37 = findViewById<TextView>(R.id.o_b37)
    private val o_b40 = findViewById<TextView>(R.id.o_b40)
    private val o_b41 = findViewById<TextView>(R.id.o_b41)
    private val o_b42 = findViewById<TextView>(R.id.o_b42)
    private val o_b43 = findViewById<TextView>(R.id.o_b43)
    private val o_b44 = findViewById<TextView>(R.id.o_b44)
    private val o_b45 = findViewById<TextView>(R.id.o_b45)
    private val o_b46 = findViewById<TextView>(R.id.o_b46)
    private val o_b47 = findViewById<TextView>(R.id.o_b47)
    private val o_b50 = findViewById<TextView>(R.id.o_b50)
    private val o_b51 = findViewById<TextView>(R.id.o_b51)
    private val o_b52 = findViewById<TextView>(R.id.o_b52)
    private val o_b53 = findViewById<TextView>(R.id.o_b53)
    private val o_b54 = findViewById<TextView>(R.id.o_b54)
    private val o_b55 = findViewById<TextView>(R.id.o_b55)
    private val o_b56 = findViewById<TextView>(R.id.o_b56)
    private val o_b57 = findViewById<TextView>(R.id.o_b57)
    private val o_b60 = findViewById<TextView>(R.id.o_b60)
    private val o_b61 = findViewById<TextView>(R.id.o_b61)
    private val o_b62 = findViewById<TextView>(R.id.o_b62)
    private val o_b63 = findViewById<TextView>(R.id.o_b63)
    private val o_b64 = findViewById<TextView>(R.id.o_b64)
    private val o_b65 = findViewById<TextView>(R.id.o_b65)
    private val o_b66 = findViewById<TextView>(R.id.o_b66)
    private val o_b67 = findViewById<TextView>(R.id.o_b67)
    private val o_b70 = findViewById<TextView>(R.id.o_b70)
    private val o_b71 = findViewById<TextView>(R.id.o_b71)
    private val o_b72 = findViewById<TextView>(R.id.o_b72)
    private val o_b73 = findViewById<TextView>(R.id.o_b73)
    private val o_b74 = findViewById<TextView>(R.id.o_b74)
    private val o_b75 = findViewById<TextView>(R.id.o_b75)
    private val o_b76 = findViewById<TextView>(R.id.o_b76)
    private val o_b77 = findViewById<TextView>(R.id.o_b77)

    private val blackTurn = findViewById<LinearLayout>(R.id.blackTurn)
    private val whiteTurn = findViewById<LinearLayout>(R.id.whiteTurn)

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
                            val checkmate_okButton = findViewById<Button>(R.id.checkmate_okButton)
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(checkMateView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 500)

                            val okButton: TextView = checkmate_okButton
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
                            val checkmate_okButton = findViewById<Button>(R.id.checkmate_okButton)
                            checkMateView.rotation = 180F
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(checkMateView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 500)

                            val okButton: TextView = checkmate_okButton
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
                            val check_okButton = findViewById<Button>(R.id.check_okButton)
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(checkView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 360)

                            val okButton: TextView = check_okButton
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
                            val check_okButton = findViewById<Button>(R.id.check_okButton)
                            checkView.rotation = 180F
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(checkView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 360)

                            val okButton: TextView = check_okButton
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
                            val stalemate_okButton = findViewById<Button>(R.id.stalemate_okButton)
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
                            builder.setView(staleMateView)
                            val dialog = builder.create()
                            dialog.show()
                            dialog.window?.setLayout(650, 500)

                            val okButton: TextView = stalemate_okButton
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
                            val stalemate_okButton = findViewById<Button>(R.id.stalemate_okButton)
                            staleMateView.rotation = 180F
                            val builder = AlertDialog.Builder(this@OneDeviceGameActivity)
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