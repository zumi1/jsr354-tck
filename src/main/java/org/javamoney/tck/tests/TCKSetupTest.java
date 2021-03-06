/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch). Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.tck.tests;

import org.javamoney.tck.TCKTestSetup;
import org.jboss.test.audit.annotations.SpecAssertion;
import org.jboss.test.audit.annotations.SpecVersion;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import java.util.Collection;

@SpecVersion(spec = "JSR 354", version = "1.0.0")
public class TCKSetupTest{

    @SpecAssertion(
            section = "0",
            id = "Setup",
            note = "Tests that a TestConfiguration is registered with the JDK's ServiceLoader.")
    @Test(description = "TCK Setup: ensure TCK Configuration is registered and available.")
    public void testTestSetup(){
        AssertJUnit.assertTrue("TCK Configuration not available.", TCKTestSetup.getTestConfiguration() != null);
        AssertJUnit.assertNotNull(TCKTestSetup.getTestConfiguration());
    }

    @SpecAssertion(
            section = "0",
            id = "Setup",
            note = "Checks that TestConfiguration.getAmountClasses() returns a non empty collection of amount " +
                    "implementations")
    @Test(description = "TChecks that MonetaryAmount classes are registered for testing.")
    public void testTestAmountConfiguration(){
        Collection<Class> amountClasses = TCKTestSetup.getTestConfiguration().getAmountClasses();
        AssertJUnit.assertNotNull("TCK Test Configuration amount classes are null.", amountClasses);
        AssertJUnit.assertFalse("TCK Test Configuration amount classes is empty.", amountClasses.isEmpty());
    }


}
