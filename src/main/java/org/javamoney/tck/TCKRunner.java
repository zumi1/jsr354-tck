/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch). Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.tck;

import org.javamoney.tck.tests.*;
import org.javamoney.tck.tests.conversion.ConvertingAmountsTest;
import org.javamoney.tck.tests.conversion.ExchangeRatesAndRateProvidersTest;
import org.javamoney.tck.tests.conversion.MonetaryConversionsTest;
import org.javamoney.tck.tests.conversion.ProviderChainsTest;
import org.javamoney.tck.tests.format.FormattingMonetaryAmountsTest;

import org.testng.IReporter;
import org.testng.ITestListener;
import org.testng.TestNG;
import org.testng.reporters.*;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anatole on 12.06.2014.
 */
public class TCKRunner extends XmlSuite{
    public TCKRunner(){
        setName("JSR354-TCK 1.0");
        XmlTest test = new XmlTest(this);
        test.setName("TCK/Test Setup");
        List<XmlClass> classes = new ArrayList<XmlClass>();
        classes.add(new XmlClass(TCKTestSetup.class));
        classes.add(new XmlClass(ModellingCurrenciesTest.class));
        classes.add(new XmlClass(ModellingMonetaryAmountsTest.class));
        classes.add(new XmlClass(CreatingMonetaryAmountsTest.class));
        classes.add(new XmlClass(ExternalizingNumericValueTest.class));
        classes.add(new XmlClass(FunctionalExtensionPointsTest.class));
        classes.add(new XmlClass(AccessingCurrenciesAmountsRoundingsTest.class));
        classes.add(new XmlClass(MonetaryConversionsTest.class));
        classes.add(new XmlClass(ExchangeRatesAndRateProvidersTest.class));
        classes.add(new XmlClass(ConvertingAmountsTest.class));
        classes.add(new XmlClass(ProviderChainsTest.class));
        classes.add(new XmlClass(FormattingMonetaryAmountsTest.class));
        test.setXmlClasses(classes);
    }

    public static final void main(String... args){
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(new TCKRunner());
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.setOutputDirectory("./tck-results");
        tng.addListener(new VerboseReporter());
        tng.addListener(new SuiteHTMLReporter());
        tng.run();
    }

}
