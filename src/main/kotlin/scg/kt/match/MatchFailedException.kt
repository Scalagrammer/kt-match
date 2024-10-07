package scg.kt.match

data class MatchFailedException(val argument: Any?): IllegalArgumentException(argument.toString())