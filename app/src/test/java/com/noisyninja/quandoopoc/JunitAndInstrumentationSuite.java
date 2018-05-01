package com.noisyninja.quandoopoc;

import com.noisyninja.quandoopoc.instrumentation.DetailPresenterTest;
import com.noisyninja.quandoopoc.instrumentation.MainPresenterTest;
import com.noisyninja.quandoopoc.junit.UnitTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by sudiptadutta on 01/05/18.
 */

@RunWith(Suite.class)

@Suite.SuiteClasses({
        DetailPresenterTest.class,
        UnitTest.class,
        MainPresenterTest.class
})

public class JunitAndInstrumentationSuite {

}
