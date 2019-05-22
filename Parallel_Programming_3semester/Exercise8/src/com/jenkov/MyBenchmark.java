/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jenkov;

import com.company.Main;
import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;



@Fork(1)
@Warmup(iterations = 1, time = 5)
@Measurement(iterations = 6, time = 5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class MyBenchmark {

    @State(Scope.Thread)
    public static class MyState {

        @Setup(Level.Trial)
        public void doSetup() {
            A = new int[1000001];
            B = new int[1000001];
            for (int i = 0; i < 1000001; i++) {
                A[i] = 7;
                B[i] = 6;
            }
        }

        @TearDown(Level.Trial)
        public void doTearDown() {}
        int[] A;
        int[] B;
        int res;

    }


    @Benchmark
    public void testTask8_Single(MyState state) {
        for (int i = 0; i < 10; i++) {
            state.res = Main.singleAlgo(state.A, state.B);
        }
    }

    @Benchmark
    public void testTask8_1(MyState state) {
        for (int i = 0; i < 10; i++) {
            Main.setNumOfThreads(1);
            state.res = Main.multiAlgo(state.A, state.B);
        }
    }

    @Benchmark
    public void testTask8_2(MyState state) {
        for (int i = 0; i < 10; i++) {
            Main.setNumOfThreads(2);
            state.res = Main.multiAlgo(state.A, state.B);
        }
    }

    @Benchmark
    public void testTask8_4(MyState state) {
        for (int i = 0; i < 10; i++) {
            Main.setNumOfThreads(4);
            state.res = Main.multiAlgo(state.A, state.B);
        }
    }

    @Benchmark
    public void testTask8_8(MyState state) {
        for (int i = 0; i < 10; i++) {
            Main.setNumOfThreads(8);
            state.res = Main.multiAlgo(state.A, state.B);
        }
    }


}
