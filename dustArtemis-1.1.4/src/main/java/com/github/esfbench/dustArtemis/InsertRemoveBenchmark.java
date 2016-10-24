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

package com.github.esfbench.dustArtemis;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Setup;

import com.artemis.Aspect;
import com.artemis.World;
import com.github.esfbench.JmhSettings;
import com.github.esfbench.dustArtemis.component.*;
import com.github.esfbench.dustArtemis.system.CompSystemA;
import com.github.esfbench.dustArtemis.system.CompSystemB;
import com.github.esfbench.dustArtemis.system.CompSystemC;
import com.github.esfbench.dustArtemis.system.CompSystemD;
import com.github.esfbench.dustArtemis.system.CompSystemE;
import com.github.esfbench.dustArtemis.system.EntityManglerSystem;

public class InsertRemoveBenchmark extends JmhSettings
{

	private World world;

	@Setup
	public void init () throws Exception
	{
		// Pass the config file with reasonable size for the benchmark.
		CfgUtils.setDustArtemisConfig( entityCount );

		world = World.builder()
				.componentTypes(
						Comp1.class,
						Comp2.class,
						Comp3.class,
						Comp4.class,
						Comp5.class,
						Comp6.class,
						Comp7.class,
						Comp8.class,
						Comp9.class,
						Comp10.class,
						Comp11.class,
						Comp12.class )
				.observer( () -> new EntityManglerSystem( SEED, entityCount ) )
				.observer( CompSystemA::new, Aspect.all( Comp1.class, Comp4.class, Comp5.class ) )
				.observer( CompSystemB::new, Aspect.all( Comp2.class, Comp8.class, Comp9.class ) )
				.observer( CompSystemC::new, Aspect.all( Comp1.class, Comp7.class, Comp9.class ).exclude( Comp2.class ) )
				.observer( CompSystemD::new, Aspect.all( Comp6.class, Comp7.class, Comp8.class ).exclude( Comp9.class ) )
				.observer( CompSystemE::new, Aspect.all( Comp10.class, Comp11.class, Comp12.class ).exclude( Comp9.class ) )
				.build();
	}

	@Benchmark
	public void insert_remove ()
	{
		world.process();
	}

	public static void main ( String[] args ) throws Exception
	{
		InsertRemoveBenchmark irb = new InsertRemoveBenchmark();
		irb.entityCount = 1024;
		irb.init();
		for ( int i = 0, s = 10000; s > i; i++ )
		{
			irb.insert_remove();
		}
	}
}
