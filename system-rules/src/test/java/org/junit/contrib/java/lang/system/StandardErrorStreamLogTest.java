package org.junit.contrib.java.lang.system;

import static java.lang.System.err;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import java.io.PrintStream;

import org.junit.Test;
import org.junit.runners.model.Statement;

public class StandardErrorStreamLogTest {
	private static final String ARBITRARY_TEXT = "arbitrary text";

	private final StandardErrorStreamLog log = new StandardErrorStreamLog();

	@Test
	public void logWriting() throws Throwable {
		executeRuleWithStatement();
		assertThat(log.getLog(), is(equalTo(ARBITRARY_TEXT)));
	}

	@Test
	public void restoreSystemErrorStream() throws Throwable {
		PrintStream originalStream = err;
		executeRuleWithStatement();
		assertThat(originalStream, is(sameInstance(err)));
	}

	private void executeRuleWithStatement() throws Throwable {
		log.apply(new WriteTextToStandardErrorStream(), null).evaluate();
	}

	private class WriteTextToStandardErrorStream extends Statement {
		@Override
		public void evaluate() throws Throwable {
			err.print(ARBITRARY_TEXT);
		}
	}
}
