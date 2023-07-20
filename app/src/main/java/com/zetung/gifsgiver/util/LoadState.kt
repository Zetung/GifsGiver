package com.zetung.gifsgiver.util

sealed class LoadState{
    class NotStarted (var msg:String = "NS") : LoadState()
    class Loading (var msg:String = "L") : LoadState()
    class Done (var msg:String = "D"): LoadState()
    class Error (var msg:String = "E"): LoadState()
}
