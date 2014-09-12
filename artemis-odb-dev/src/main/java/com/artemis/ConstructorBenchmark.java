/*
 * Copyright (c) 2005, 2014, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package com.artemis;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;


/*
 Benchmark                                            Mode  Samples    Score  Score error   Units
c.a.ConstructorBenchmark.create_handle              thrpt        9   35.856        0.616  ops/us
c.a.ConstructorBenchmark.create_handle_unreflect    thrpt        9   35.964        0.394  ops/us
c.a.ConstructorBenchmark.create_plain               thrpt        9  352.515        5.724  ops/us
c.a.ConstructorBenchmark.create_reflect             thrpt        9  132.363        1.908  ops/us

 */
@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@Threads(1)
@Fork(3)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 5, timeUnit = TimeUnit.SECONDS)
public class ConstructorBenchmark {
    
    private Constructor<SimpleObject> constructor;
    private MethodHandle constructorHandle;
    private MethodHandle constructorUnreflectHandle;
    
    @Setup
    public void init() throws Exception{
        constructor = SimpleObject.class.getConstructor();
        constructorHandle = MethodHandles.lookup().findConstructor(SimpleObject.class, MethodType.methodType(void.class));
        constructorUnreflectHandle = MethodHandles.lookup().unreflectConstructor(constructor);
    }        
    
    @Benchmark
    public SimpleObject create_reflect() throws Throwable {
        return constructor.newInstance();
    }
    
    @Benchmark @SuppressWarnings("static-method")
    public SimpleObject create_plain() throws Throwable {
        return new SimpleObject();
    }
    
    @Benchmark
    public SimpleObject create_handle() throws Throwable {
        return (SimpleObject)constructorHandle.invokeExact();
    }
    
    @Benchmark
    public SimpleObject create_handle_unreflect() throws Throwable {
        return (SimpleObject)constructorUnreflectHandle.invokeExact();
    }
    
    public static void main(String[] args) throws RunnerException {
        new Runner(
            new OptionsBuilder()
                .include(".*special.*")
                .build())
        .run();
    }
    
    public static final class SimpleObject {}
}
