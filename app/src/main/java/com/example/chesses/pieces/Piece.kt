package com.example.chesses.pieces

import com.example.chesses.board.ChessBoard

/**
 * 棋子基類
 * 所有棋子都應繼承此類
 */
abstract class Piece(
    val type: PieceType,
    val isRed: Boolean  // true = 紅方（上方），false = 黑方（下方）
) {
    /**
     * 獲取該棋子的可移動位置
     * @param board 當前棋盤
     * @param currentRow 棋子當前行座���
     * @param currentCol 棋子當前列座標
     * @return 可移動位置列表
     */
    abstract fun getAvailableMoves(
        board: ChessBoard,
        currentRow: Int,
        currentCol: Int
    ): List<Pair<Int, Int>>

    /**
     * 獲取棋子的中文名稱
     */
    abstract fun getDisplayName(): String
}
