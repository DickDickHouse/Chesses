package com.example.chesses.board

import com.example.chesses.pieces.Piece

/**
 * 棋盤邏輯管理類
 * 負責棋盤狀態、棋子位置等邏輯運算
 * 不涉及UI渲染
 */
class ChessBoard {
    // 棋盤網格 [行][列]
    private val grid = Array(BoardConfig.ROWS) { Array(BoardConfig.COLS) { null as Piece? } }
    
    // 選中的棋子座標
    var selectedPiece: Pair<Int, Int>? = null
    
    // 可移動位置列表
    var availableMoves: MutableList<Pair<Int, Int>> = mutableListOf()

    /**
     * 初始化棋盤（擺放所有棋子）
     */
    fun initializeBoard() {
        // 清空棋盤
        for (i in 0 until BoardConfig.ROWS) {
            for (j in 0 until BoardConfig.COLS) {
                grid[i][j] = null
            }
        }
        
        // TODO: 在這裡擺放棋子
        // 這將在Piece類建立後實現
    }

    /**
     * 獲取指定位置的棋子
     * @param row 行座標
     * @param col 列座標
     */
    fun getPiece(row: Int, col: Int): Piece? {
        return if (isValidPosition(row, col)) grid[row][col] else null
    }

    /**
     * 在指定位置放置棋子
     */
    fun setPiece(row: Int, col: Int, piece: Piece?) {
        if (isValidPosition(row, col)) {
            grid[row][col] = piece
        }
    }

    /**
     * 選擇棋子並計算可移動位置
     */
    fun selectPiece(row: Int, col: Int): Boolean {
        val piece = getPiece(row, col)
        return if (piece != null) {
            selectedPiece = Pair(row, col)
            availableMoves = piece.getAvailableMoves(this, row, col).toMutableList()
            true
        } else {
            false
        }
    }

    /**
     * 取消選擇
     */
    fun deselectPiece() {
        selectedPiece = null
        availableMoves.clear()
    }

    /**
     * 移動棋子
     */
    fun movePiece(fromRow: Int, fromCol: Int, toRow: Int, toCol: Int): Boolean {
        val piece = getPiece(fromRow, fromCol) ?: return false
        
        // 驗證目標位置是否為可移動位置
        if (!availableMoves.contains(Pair(toRow, toCol))) {
            return false
        }
        
        // 移除目標位置的棋子（如果有）
        setPiece(toRow, toCol, piece)
        setPiece(fromRow, fromCol, null)
        
        deselectPiece()
        return true
    }

    /**
     * 驗證位置是否有效
     */
    fun isValidPosition(row: Int, col: Int): Boolean {
        return row >= 0 && row < BoardConfig.ROWS && col >= 0 && col < BoardConfig.COLS
    }

    /**
     * 獲取所有棋子（用於渲染）
     */
    fun getAllPieces(): List<Triple<Int, Int, Piece>> {
        val pieces = mutableListOf<Triple<Int, Int, Piece>>()
        for (i in 0 until BoardConfig.ROWS) {
            for (j in 0 until BoardConfig.COLS) {
                val piece = grid[i][j]
                if (piece != null) {
                    pieces.add(Triple(i, j, piece))
                }
            }
        }
        return pieces
    }

    /**
     * 檢查該位置是否在河區域
     */
    fun isInRiver(row: Int): Boolean {
        return row == BoardConfig.RIVER_ROW
    }

    /**
     * 檢查該位置是否在宮區
     */
    fun isInPalace(row: Int, col: Int): Boolean {
        val inRedPalace = row in 0..2 && col in 3..5
        val inBlackPalace = row in 7..9 && col in 3..5
        return inRedPalace || inBlackPalace
    }
}
