package com.example.chessgame

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.chessgame.Pieces.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var clickedTilePosition: Position
    private var boardPosition = Array(8) {
        Array<Piece>(8) {
            Empty()
        }
    }

    private val onMoveListener = View.OnClickListener {
        val intId = it.id
        val id = resources.getResourceEntryName(intId)
        val x = Integer.parseInt(id.get(1).toString())
        val y = Integer.parseInt(id.get(2).toString())

        val clickedTileX = clickedTilePosition.x
        val clickedTileY = clickedTilePosition.y

        val touchPiece = boardPosition[x][y]

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

            boardPosition[clickedTileX][clickedTileY] = Empty()

            when (clickedTileType) {
                "Pawn" -> boardPosition[x][y] = Pawn(clickedTileColor)
                "Rook" -> boardPosition[x][y] = Rook(clickedTileColor)
                "Knight" -> boardPosition[x][y] = Knight(clickedTileColor)
                "Bishop" -> boardPosition[x][y] = Bishop(clickedTileColor)
                "Queen" -> boardPosition[x][y] = Queen(clickedTileColor)
                "King" -> boardPosition[x][y] = King(clickedTileColor)
            }

            updateUi()
            resetBoardColor()
            setListener()
        } else {
            resetBoardColor()
            setListener()
        }

        for(i in 0..7){
            for(j in 0..7){
                boardPosition[i][j].onCanMove=false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        for(i in 0..7){
            for(j in 0..7){
                boardPosition[x][y].onCanMove=false
            }
        }

        when (pieceKind(boardPosition[x][y])) {
            "Pawn" -> canMovePositions =
                (boardPosition[x][y] as Pawn).getCanMoveArea(position, boardPosition)
            "Rook" -> canMovePositions =
                (boardPosition[x][y] as Rook).getCanMoveArea(position, boardPosition)
            "Knight" -> canMovePositions =
                (boardPosition[x][y] as Knight).getCanMoveArea(position, boardPosition)
            "Bishop" -> canMovePositions =
                (boardPosition[x][y] as Bishop).getCanMoveArea(position, boardPosition)
            "Queen" -> canMovePositions =
                (boardPosition[x][y] as Queen).getCanMoveArea(position, boardPosition)
            "King" -> canMovePositions =
                (boardPosition[x][y] as King).getCanMoveArea(position, boardPosition)
            "Empty" -> canMovePositions =
                (boardPosition[x][y] as Empty).getCanMoveArea(position, boardPosition)
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
        canMovePositions.clear()

        when (pieceKind(boardPosition[x][y])) {
            "Pawn" -> canEatPosition =
                (boardPosition[x][y] as Pawn).isCanEat(position, boardPosition)
            "Rook" -> canEatPosition =
                (boardPosition[x][y] as Rook).isCanEat(position, boardPosition)
            "Knight" -> canEatPosition =
                (boardPosition[x][y] as Knight).isCanEat(position, boardPosition)
            "Bishop" -> canEatPosition =
                (boardPosition[x][y] as Bishop).isCanEat(position, boardPosition)
            "Queen" -> canEatPosition =
                (boardPosition[x][y] as Queen).isCanEat(position, boardPosition)
            "King" -> canEatPosition =
                (boardPosition[x][y] as King).isCanEat(position, boardPosition)
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
        setMoveListener()
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
}