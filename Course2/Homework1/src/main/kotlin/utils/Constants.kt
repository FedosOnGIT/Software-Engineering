package utils

class Constants {
    companion object {
        const val DUCKDUCKGO_SEARCH_URL: String = "https://duckduckgo.com/html/?q="
        fun GOOGLE_SEARCH_URL(key: String, cx: String, query: String, number: Int): String {
            return "https://customsearch.googleapis.com/customsearch/v1" +
                    "?key=$key" +
                    "&cx=$cx" +
                    "&q=$query" +
                    "&num=$number"
        }
    }
}