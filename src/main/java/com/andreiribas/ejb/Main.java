/* 
The MIT License (MIT)

Copyright (c) 2013 Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/

/**
 * 
 */
package com.andreiribas.ejb;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.log4j.Logger;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@Singleton
@Startup
public class Main {
	
	private Logger LOGGER;
	
	@EJB(name="asyncCalculator")
	private AsyncCalculator asyncCalculatorBean;
	
	@PostConstruct
	public void start() {
		
		this.LOGGER = Logger.getLogger(Main.class);
	
		LOGGER.debug("Calling Async method on AsyncCalculator bean.");
		
		Future<Double> asyncCalcResult = asyncCalculatorBean.calc();
		
		LOGGER.debug("Async method on AsyncCalculator bean returned. Now Future.get() method will be called on the Future object.");
		
		Double calcResult = null;
		
		try {
			
			calcResult = asyncCalcResult.get();
			
			LOGGER.debug(String.format("Finished calling Future.get() method on future object, and the result is %s.", calcResult));
			
		} catch (InterruptedException e) {
			LOGGER.debug("Called Future.get() method and got InterruptedException!");
			e.printStackTrace();
		} catch (ExecutionException e) {
			LOGGER.debug("Called Future.get() method and got ExecutionException!");
			e.printStackTrace();
		}
		
	}
	
}
