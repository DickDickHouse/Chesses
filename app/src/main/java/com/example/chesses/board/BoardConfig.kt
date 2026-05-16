package com.example.chesses.board

/**
 * 棋盤配置常數
 * 定義棋盤的邏輯尺寸和視覺參數
 */
object BoardConfig {
    // 棋盤邏輯尺寸
    const val BOARD_WIDTH = 800      // 寬度（邏輯座標）
    const val BOARD_HEIGHT = 900     // 高度（邏輯座標）
    const val CELL_SIZE = 100        // 每格大小
    const val RIVER_HEIGHT = 100     // 楚河漢界高度

    // 棋盤行列數
    const val COLS = 9               // 9列（橫向）
    const val ROWS = 10              // 10行（縱向）
    const val RIVER_ROW = 5          // 河所在的行（0-4為紅方，5-9為黑方）

    // 視覺參數
    object Colors {
        const val BOARD_COLOR = 0xFFD4A574.toInt()      // 棋盤背景色（木色）
        const val GRID_LINE_COLOR = 0xFF2F1F0F.toInt()  // 格線顏色（深褐色）
        const val PALACE_LINE_COLOR = 0xFFFF0000.toInt()// 宮區對角線（紅色）
        const val RIVER_COLOR = 0xFFE8D4C4.toInt()      // 河區域顏色
        const val SELECTED_CELL_COLOR = 0x80FFFF00.toInt() // 選中格子（半透明黃）
    }

    object Sizes {
        const val GRID_LINE_WIDTH = 2f                  // 格線寬度
        const val PALACE_LINE_WIDTH = 3f                // 宮區線寬度
        const val PIECE_RADIUS = 40f                    // 棋子半徑
    }

    // 縮放參數
    const val MIN_ZOOM = 0.5f
    const val MAX_ZOOM = 2.5f
    const val DEFAULT_ZOOM = 1.0f
}
