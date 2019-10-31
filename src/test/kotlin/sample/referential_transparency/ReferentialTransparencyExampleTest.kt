package sample.referential_transparency

import arrow.fx.IO
import arrow.fx.extensions.fx
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test


class ReferentialTransparencyExampleTest {



    fun `p1 with duplication`(): Int {

        val result1: Int = doSomething(3)
        val result2: Int = doSomething(3)

        val total = result1 + result2;

        return total;
    }

    fun `p1 refactored`(): Int {

        val result: Int = doSomething(3)

        val total = result + result;

        return total;
    }

    fun `custom solution with duplication`(): AmmarioSideEffect<Int> {

        var result1 = AmmarioSideEffect { doSomething(3) }
        var result2 = AmmarioSideEffect { doSomething(3) }

        return result1 + result2
    }

    fun `custom solution refactored`(): AmmarioSideEffect<Int> {

            var result = AmmarioSideEffect { doSomething(3) }

            return result + result
    }

    fun `solution with arrow-io with duplication`(): IO<Int> {

        return IO.fx {

            var result1 = IO { doSomething(3) }
            var result2 = IO { doSomething(3) }

            !result1 + !result2
        }


    }
   fun `solution with arrow-io refactored`(): IO<Int> {

        return IO.fx {

            var result = IO { doSomething(3) }

            !result + !result
        }

    }

    @Test
    fun compareResults() {

        val `p1 with duplication`               = executeProgram( { `p1 with duplication`() })
        val `p1 refactored`                     = executeProgram( { `p1 refactored`() })

        val `custom solution with duplication`  = executeProgram( { `custom solution with duplication`().unsafeRunSync() })
        val `custom solution refactored`        = executeProgram( { `custom solution refactored`().unsafeRunSync() })

        val `solution with arrow-io with duplication`  = executeProgram( { `solution with arrow-io with duplication`().unsafeRunSync() })
        val `solution with arrow-io refactored`        = executeProgram( { `solution with arrow-io refactored`().unsafeRunSync() })

        println("p1 with duplication result: $`p1 with duplication`")
        println("p1 refactored result:  $`p1 refactored`")
        println()

        println("custom solution with duplication result: $`custom solution with duplication`")
        println("custom solution refactored result: $`custom solution refactored`")
        println()

        println("solution with arrow-io with duplication result: $`solution with arrow-io with duplication`")
        println("solution with arrow-io refactored result: $`solution with arrow-io refactored`")
        println()

        assertThat("different results custom solution: ",`custom solution refactored` ,`is`(`custom solution with duplication`))
        assertThat("different results solution with arrow-io: ",`solution with arrow-io refactored` ,`is`(`solution with arrow-io with duplication`))

        assertThat("different results with p1 - p1 is not referential transparent !",`p1 refactored` ,`is`(`p1 with duplication`))

    }

    private fun executeProgram(f: () -> Int): Int {
        resetState()

        return f();
    }








    // doSomething - try to run examples without looking at the implementation
    // but just the signature
    fun doSomething(input: Int): Int {
        if (availableSeats >= input)
        {
            availableSeats= availableSeats -input
            return input
        }
        else return 0

    }

    val A_CONST = 4
    var availableSeats = A_CONST

    private fun resetState() {
        availableSeats = A_CONST
    }


}
