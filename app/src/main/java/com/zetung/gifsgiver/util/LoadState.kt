package com.zetung.gifsgiver.util

import com.zetung.gifsgiver.repository.model.HomeGifsModel

sealed class LoadState{
    class Loading (var msg:String = "L") : LoadState()
    class Done (var msg:String = "D"): LoadState()
    class Error (var msg:String = "E"): LoadState()
}
