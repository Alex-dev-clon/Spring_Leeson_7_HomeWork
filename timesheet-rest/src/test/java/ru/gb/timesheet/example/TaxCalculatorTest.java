package ru.gb.timesheet.example;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaxCalculatorTest {

    @Test
    void testGetPriceWithTax() {
        TaxResolver mock = mock(TaxResolver.class);
        //when(mock.getCurrentTax()).thenReturn(0.2);
        doReturn(0.2).when(mock).getCurrentTax();

        TaxCalculator taxCalculator = new TaxCalculator(mock);
        assertEquals(120.0, taxCalculator.getPriceWithTax(100.0));

        verify(mock).getCurrentTax();
    }
}