package com.example.chesses.utils

import com.example.chesses.board.BoardConfig

/**
 * 坐標轉換工具類
 * 處理邏輯座標和屏幕座標之間的轉換
 */
object CoordinateUtils {
    
    /**
     * 將屏幕座標轉換為棋盤邏輯座標
     * @param screenX 屏幕X座標（像素）
     * @param screenY 屏幕Y座標（像素）
     * @param zoom 當前縮放級別
     * @param offsetX 水平偏移
     * @param offsetY 垂直偏移
     * @return Pair(行, 列) 如果不在棋盤內返回 (-1, -1)
     */
    fun screenToBoard(
        screenX: Float,
        screenY: Float,
        zoom: Float,
        offsetX: Float,
        offsetY: Float
    ): Pair<Int, Int> {
        // 移除偏移
        val adjustedX = (screenX - offsetX) / zoom
        val adjustedY = (screenY - offsetY) / zoom

        // 驗證是否在棋盤範圍內
        if (adjustedX < 0 || adjustedX > BoardConfig.BOARD_WIDTH ||
            adjustedY < 0 || adjustedY > BoardConfig.BOARD_HEIGHT
        ) {
            return Pair(-1, -1)
        }

        // 計算行列
        val col = (adjustedX / BoardConfig.CELL_SIZE).toInt()
        val row = (adjustedY / BoardConfig.CELL_SIZE).toInt()

        return if (row in 0 until BoardConfig.ROWS && col in 0 until BoardConfig.COLS) {
            Pair(row, col)
        } else {
            Pair(-1, -1)
        }
    }

    /**
     * 將棋盤邏輯座標轉換為屏幕座標
     * @param row 棋盤行座標
     * @param col 棋盤列座標
     * @param zoom 當前縮放級別
     * @param offsetX 水平偏移
     * @param offsetY 垂直偏移
     * @return Pair(屏幕X, 屏幕Y)
     */
    fun boardToScreen(
        row: Int,
        col: Int,
        zoom: Float,
        offsetX: Float,
        offsetY: Float
    ): Pair<Float, Float> {
        val logicalX = col * BoardConfig.CELL_SIZE + BoardConfig.CELL_SIZE / 2f
        val logicalY = row * BoardConfig.CELL_SIZE + BoardConfig.CELL_SIZE / 2f

        val screenX = logicalX * zoom + offsetX
        val screenY = logicalY * zoom + offsetY

        return Pair(screenX, screenY)
    }

    /**
     * 獲取棋子中心的邏輯座標
     */
    fun getCellCenter(row: Int, col: Int): Pair<Float, Float> {
        val x = col * BoardConfig.CELL_SIZE + BoardConfig.CELL_SIZE / 2f
        val y = row * BoardConfig.CELL_SIZE + BoardConfig.CELL_SIZE / 2f
        return Pair(x, y)
    }
}
