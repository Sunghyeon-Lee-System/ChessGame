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

class BlackPawnPromotionDialog(context: Context) : Dialog(context), View.OnClickListener {
    private lateinit var btnQueen: ImageButton
    private lateinit var btnRook: ImageButton
    private lateinit var btnBishop: ImageButton
    private lateinit var btnKnight: ImageButton

    private var dialogListener: BlackDialogListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.black_promotion_dialog)
        this.setCancelable(false)

        btnQueen = this.findViewById(R.id.bqueen)
        btnRook = this.findViewById(R.id.brook)
        btnBishop = this.findViewById(R.id.bbishop)
        btnKnight = this.findViewById(R.id.bknight)

        btnQueen.setOnClickListener(this)
        btnRook.setOnClickListener(this)
        btnBishop.setOnClickListener(this)
        btnKnight.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.bqueen -> dialogListener?.onClicked(Queen(false))
            R.id.brook -> dialogListener?.onClicked(Rook(false))
            R.id.bbishop -> dialogListener?.onClicked(Bishop(false))
            R.id.bknight -> dialogListener?.onClicked(Knight(false))
        }
        this.dismiss()
    }

    fun setDialogListener(dialogListener: BlackDialogListener){
        this.dialogListener=dialogListener
    }
}
