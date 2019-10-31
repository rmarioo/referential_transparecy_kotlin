package sample.referential_transparency


sealed class AmmarioSideEffect<A>
{
    abstract fun unsafeRunSync(): A

    companion object {
        operator fun <A> invoke(f: () -> A): AmmarioSideEffect<A> =
            Suspend(f)
    }

    data class Suspend<A>(val f: () -> A) : AmmarioSideEffect<A>() {
        override fun unsafeRunSync(): A = f()
    }
}

operator fun AmmarioSideEffect<Int>.plus(v: AmmarioSideEffect<Int>): AmmarioSideEffect<Int> {

    return AmmarioSideEffect { unsafeRunSync() + v.unsafeRunSync() }
}


