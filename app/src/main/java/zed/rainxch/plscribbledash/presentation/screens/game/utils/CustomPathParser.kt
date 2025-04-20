package zed.rainxch.plscribbledash.presentation.screens.game.utils

class CustomPathParser {
    private val standardParser = androidx.compose.ui.graphics.vector.PathParser()

    /**
     * Parses an SVG path string and converts it to a Path object
     * Filters out or replaces unsupported commands before passing to standard parser
     */
    fun parsePathString(pathData: String): androidx.compose.ui.graphics.Path {
        try {
            // First attempt with standard parser
            return standardParser.parsePathString(pathData).toPath()
        } catch (e: IllegalArgumentException) {
            // If standard parsing fails, try with custom handling
            val sanitizedPath = sanitizePathData(pathData)
            return standardParser.parsePathString(sanitizedPath).toPath()
        }
    }

    /**
     * Sanitizes path data by removing or replacing unsupported commands
     */
    private fun sanitizePathData(pathData: String): String {
        val result = StringBuilder()
        var i = 0

        while (i < pathData.length) {
            val char = pathData[i]

            // Handle the 'P' command specifically
            if (char == 'P') {
                // Skip this command and its parameters
                // Find the next command character (uppercase or lowercase letter)
                i++
                while (i < pathData.length &&
                      !pathData[i].isLetter() &&
                      pathData[i] != '-' &&
                      pathData[i] != '.') {
                    i++
                }
            } else {
                // Keep other valid SVG commands
                result.append(char)
                i++
            }
        }

        return result.toString()
    }
}
