package com.example.chessgame

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
import com.example.chessgame.pieces.Bishop
import com.example.chessgame.pieces.Knight
import com.example.chessgame.pieces.Queen
import com.example.chessgame.pieces.Rook

class WhitePawnPromotionDialog(context: Context) : Dialog(context), View.OnClickListener {
    private lateinit var btnQueen: ImageButton
    private lateinit var btnRook: ImageButton
    private lateinit var btnBishop: ImageButton
    private lateinit var btnKnight: ImageButton

    private var dialogListener: WhiteDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.white_promotion_dialog)
        this.setCancelable(false)

        btnQueen = this.findViewById(R.id.wqueen)
        btnRook = this.findViewById(R.id.wrook)
        btnBishop = this.findViewById(R.id.wbishop)
        btnKnight = this.findViewById(R.id.wknight)

        btnQueen.setOnClickListener(this)
        btnRook.setOnClickListener(this)
        btnBishop.setOnClickListener(this)
        btnKnight.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.wqueen -> dialogListener?.onClicked(Queen(true))
            R.id.wrook -> dialogListener?.onClicked(Rook(true))
            R.id.wbishop -> dialogListener?.onClicked(Bishop(true))
            R.id.wknight -> dialogListener?.onClicked(Knight(true))
        }
        this.dismiss()
    }

    fun setDialogListener(dialogListener: WhiteDialogListener){
        this.dialogListener=dialogListener
    }
}
