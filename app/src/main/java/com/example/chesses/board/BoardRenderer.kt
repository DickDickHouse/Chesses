package com.example.chesses.board

import android.graphics.*
import com.example.chesses.pieces.Piece

/**
 * 棋盤渲染器
 * 負責所有棋盤的視覺繪製
 * 與棋盤邏輯分離，只處理UI顯示
 */
class BoardRenderer {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var zoomLevel = BoardConfig.DEFAULT_ZOOM
    private var offsetX = 0f
    private var offsetY = 0f

    /**
     * 設定縮放級別
     */
    fun setZoom(zoom: Float) {
        zoomLevel = zoom.coerceIn(BoardConfig.MIN_ZOOM, BoardConfig.MAX_ZOOM)
    }

    /**
     * 獲取縮放級別
     */
    fun getZoom(): Float = zoomLevel

    /**
     * 設定平移偏移
     */
    fun setOffset(x: Float, y: Float) {
        offsetX = x
        offsetY = y
    }

    /**
     * 繪製完整棋盤
     */
    fun drawBoard(canvas: Canvas) {
        // 保存當前Canvas狀態
        canvas.save()
        
        // 應用縮放和平移
        canvas.translate(offsetX, offsetY)
        canvas.scale(zoomLevel, zoomLevel)

        // 繪製背景
        drawBackground(canvas)

        // 繪製河區域
        drawRiver(canvas)

        // 繪製格線
        drawGridLines(canvas)

        // 繪製宮區
        drawPalaces(canvas)

        // 恢復Canvas狀態
        canvas.restore()
    }

    /**
     * 繪製棋盤背景
     */
    private fun drawBackground(canvas: Canvas) {
        paint.color = BoardConfig.Colors.BOARD_COLOR
        paint.style = Paint.Style.FILL
        canvas.drawRect(
            0f,
            0f,
            BoardConfig.BOARD_WIDTH.toFloat(),
            BoardConfig.BOARD_HEIGHT.toFloat(),
            paint
        )
    }

    /**
     * 繪製河區域（楚河漢界）
     */
    private fun drawRiver(canvas: Canvas) {
        paint.color = BoardConfig.Colors.RIVER_COLOR
        paint.style = Paint.Style.FILL
        
        val riverTop = BoardConfig.RIVER_ROW * BoardConfig.CELL_SIZE.toFloat()
        canvas.drawRect(
            0f,
            riverTop,
            BoardConfig.BOARD_WIDTH.toFloat(),
            riverTop + BoardConfig.RIVER_HEIGHT,
            paint
        )

        // 繪製「楚河漢界」文字
        paint.color = BoardConfig.Colors.GRID_LINE_COLOR
        paint.textSize = 24f
        paint.textAlign = Paint.Align.CENTER
        val riverCenterY = riverTop + BoardConfig.RIVER_HEIGHT / 2 + 8f

        canvas.drawText(
            "楚河",
            BoardConfig.BOARD_WIDTH / 2f,
            riverCenterY - 15f,
            paint
        )
        canvas.drawText(
            "漢界",
            BoardConfig.BOARD_WIDTH / 2f,
            riverCenterY + 15f,
            paint
        )
    }

    /**
     * 繪製格線
     */
    private fun drawGridLines(canvas: Canvas) {
        paint.color = BoardConfig.Colors.GRID_LINE_COLOR
        paint.strokeWidth = BoardConfig.Sizes.GRID_LINE_WIDTH
        paint.style = Paint.Style.STROKE

        // 繪製垂直線
        for (col in 0..BoardConfig.COLS) {
            val x = col * BoardConfig.CELL_SIZE.toFloat()
            canvas.drawLine(
                x, 0f,
                x, BoardConfig.BOARD_HEIGHT.toFloat(),
                paint
            )
        }

        // 繪製水平線
        for (row in 0..BoardConfig.ROWS) {
            val y = row * BoardConfig.CELL_SIZE.toFloat()
            canvas.drawLine(
                0f, y,
                BoardConfig.BOARD_WIDTH.toFloat(), y,
                paint
            )
        }
    }

    /**
     * 繪製宮區（紅方和黑方）
     */
    private fun drawPalaces(canvas: Canvas) {
        paint.color = BoardConfig.Colors.PALACE_LINE_COLOR
        paint.strokeWidth = BoardConfig.Sizes.PALACE_LINE_WIDTH
        paint.style = Paint.Style.STROKE

        // 紅方宮區 (0,3) - (2,5)
        drawPalace(canvas, 0, 3, 2, 5)

        // 黑方宮區 (7,3) - (9,5)
        drawPalace(canvas, 7, 3, 9, 5)
    }

    /**
     * 繪製單個宮區
     */
    private fun drawPalace(canvas: Canvas, topRow: Int, leftCol: Int, bottomRow: Int, rightCol: Int) {
        val x1 = leftCol * BoardConfig.CELL_SIZE.toFloat()
        val y1 = topRow * BoardConfig.CELL_SIZE.toFloat()
        val x2 = (rightCol + 1) * BoardConfig.CELL_SIZE.toFloat()
        val y2 = (bottomRow + 1) * BoardConfig.CELL_SIZE.toFloat()

        // 繪製宮區邊框
        canvas.drawRect(x1, y1, x2, y2, paint)

        // 繪製宮區對角線
        canvas.drawLine(x1, y1, x2, y2, paint)
        canvas.drawLine(x2, y1, x1, y2, paint)
    }

    /**
     * 繪製棋子
     */
    fun drawPiece(canvas: Canvas, row: Int, col: Int, piece: Piece, isSelected: Boolean = false) {
        canvas.save()
        
        // 應用縮放和平移
        canvas.translate(offsetX, offsetY)
        canvas.scale(zoomLevel, zoomLevel)

        val x = col * BoardConfig.CELL_SIZE + BoardConfig.CELL_SIZE / 2f
        val y = row * BoardConfig.CELL_SIZE + BoardConfig.CELL_SIZE / 2f

        // 繪製選中高亮
        if (isSelected) {
            paint.color = BoardConfig.Colors.SELECTED_CELL_COLOR
            paint.style = Paint.Style.FILL
            canvas.drawCircle(x, y, BoardConfig.Sizes.PIECE_RADIUS + 5f, paint)
        }

        // 繪製棋子圓形背景
        paint.color = if (piece.isRed) Color.RED else Color.BLACK
        paint.style = Paint.Style.FILL
        canvas.drawCircle(x, y, BoardConfig.Sizes.PIECE_RADIUS, paint)

        // 繪製棋子文字
        paint.color = Color.WHITE
        paint.textSize = 32f
        paint.textAlign = Paint.Align.CENTER
        val pieceText = piece.getDisplayName()
        canvas.drawText(pieceText, x, y + 12f, paint)

        canvas.restore()
    }

    /**
     * 繪製可移動位置標記
     */
    fun drawAvailableMove(canvas: Canvas, row: Int, col: Int) {
        canvas.save()
        
        canvas.translate(offsetX, offsetY)
        canvas.scale(zoomLevel, zoomLevel)

        val x = col * BoardConfig.CELL_SIZE + BoardConfig.CELL_SIZE / 2f
        val y = row * BoardConfig.CELL_SIZE + BoardConfig.CELL_SIZE / 2f

        paint.color = 0x80FFD700.toInt() // 半透明金色
        paint.style = Paint.Style.FILL
        canvas.drawCircle(x, y, 15f, paint)

        canvas.restore()
    }

    /**
     * 縮放計算器
     */
    fun zoomIn() {
        setZoom(zoomLevel + 0.2f)
    }

    fun zoomOut() {
        setZoom(zoomLevel - 0.2f)
    }
}
