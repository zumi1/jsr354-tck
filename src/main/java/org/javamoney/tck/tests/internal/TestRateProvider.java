/*
 * Copyright (c) 2012, 2013, Werner Keil, Credit Suisse (Anatole Tresch). Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License. Contributors: Anatole Tresch - initial version.
 */
package org.javamoney.tck.tests.internal;

import org.javamoney.tck.tests.conversion.TestExchangeRate;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryCurrencies;
import javax.money.convert.*;
import java.util.Objects;

/**
 * Created by Anatole on 26.04.2014.
 */
public class TestRateProvider implements ExchangeRateProvider{

    private ProviderContext PC = new ProviderContext.Builder("TestRateProvider", RateType.OTHER).build();
    private ConversionContext CC = new ConversionContext.Builder(PC, RateType.OTHER).build();
    private CurrencyUnit TERM = new TestCurrencyUnit("FOO");

    private CurrencyConversion CONVERSION = new CurrencyConversion(){

        @Override
        public CurrencyUnit getTermCurrency(){
            return TERM;
        }

        @Override
        public ConversionContext getConversionContext(){
            return CC;
        }

        @Override
        public ExchangeRate getExchangeRate(MonetaryAmount sourceAmount){
            return new TestExchangeRate.Builder(CC).setFactor(new TestNumberValue(2)).setBase(sourceAmount.getCurrency())
                    .setTerm(TERM).build();
        }

        @Override
        public CurrencyConversion with(ConversionContext conversionContext){
            return this;
        }

        @Override
        public MonetaryAmount apply(MonetaryAmount value){
            return value.multiply(2).getFactory().setCurrency(TERM).create();
        }
    };

    @Override
    public ProviderContext getProviderContext(){
        return PC;
    }

    @Override
    public boolean isAvailable(CurrencyUnit base, CurrencyUnit term){
        Objects.requireNonNull(base);
        Objects.requireNonNull(term);
        return "FOO".equals(term.getCurrencyCode()) || "XXX".equals(term.getCurrencyCode());
    }

    @Override
    public boolean isAvailable(CurrencyUnit base, CurrencyUnit term, ConversionContext conversionContext){
        Objects.requireNonNull(conversionContext);
        Objects.requireNonNull(base);
        return "FOO".equals(term.getCurrencyCode()) || "XXX".equals(term.getCurrencyCode());
    }

    @Override
    public boolean isAvailable(String baseCode, String termCode){
        return "Foo".equals(termCode) || "XXX".equals(termCode);
    }

    @Override
    public boolean isAvailable(String baseCode, String termCode, ConversionContext conversionContext){
        Objects.requireNonNull(conversionContext);
        Objects.requireNonNull(baseCode);
        Objects.requireNonNull(termCode);
        return "Foo".equals(termCode) || "XXX".equals(termCode);
    }

    @Override
    public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term){
        if(isAvailable(base, term)){
            return new TestExchangeRate.Builder(CC).setFactor(new TestNumberValue(2)).setBase(base)
                    .setTerm(term).build();
        }
        return null;
    }

    @Override
    public ExchangeRate getExchangeRate(CurrencyUnit base, CurrencyUnit term, ConversionContext conversionContext){
        Objects.requireNonNull(conversionContext);
        Objects.requireNonNull(base);
        Objects.requireNonNull(term);
        if(isAvailable(base, term, conversionContext)){
            return new TestExchangeRate.Builder(conversionContext).setFactor(new TestNumberValue(2)).setBase(base)
                    .setTerm(term).build();
        }
        return null;
    }

    @Override
    public ExchangeRate getExchangeRate(String baseCode, String termCode){
        if(isAvailable(baseCode, termCode)){
            return getExchangeRate(MonetaryCurrencies.getCurrency(baseCode), TERM);
        }
        return null;
    }

    @Override
    public ExchangeRate getExchangeRate(String baseCode, String termCode, ConversionContext conversionContext){
        if(isAvailable(baseCode, termCode, conversionContext)){
            return getExchangeRate(MonetaryCurrencies.getCurrency(baseCode),MonetaryCurrencies.getCurrency(termCode));
        }
        return null;
    }

    @Override
    public ExchangeRate getReversed(ExchangeRate rate){
        return null;
    }

    @Override
    public CurrencyConversion getCurrencyConversion(CurrencyUnit term){
        if(TERM.getCurrencyCode().equals(term.getCurrencyCode())){
            return CONVERSION;
        }
        return null;
    }

    @Override
    public CurrencyConversion getCurrencyConversion(CurrencyUnit term, ConversionContext conversionContext){
        if(TERM.getCurrencyCode().equals(term.getCurrencyCode()) || "XXX".equals(term.getCurrencyCode())){
            return CONVERSION;
        }
        return null;
    }

    @Override
    public CurrencyConversion getCurrencyConversion(String termCode){
        if(TERM.getCurrencyCode().equals(termCode)){
            return CONVERSION;
        }
        return null;
    }

    @Override
    public CurrencyConversion getCurrencyConversion(String termCode, ConversionContext conversionContext){
        if(TERM.getCurrencyCode().equals(termCode)){
            return CONVERSION;
        }
        return null;
    }
}
