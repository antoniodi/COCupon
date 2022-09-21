package com.meli.cupon.testutils

import com.twitter.finagle.Dtab$

class DtabAdapter {

    private DtabAdapter() {

    }

    static void setBase(String dtab) {
        Dtab$.MODULE$.setBase(Dtab$.MODULE$.read(dtab))
    }
}
