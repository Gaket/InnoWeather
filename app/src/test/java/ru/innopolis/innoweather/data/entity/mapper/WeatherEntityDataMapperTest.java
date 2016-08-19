package ru.innopolis.innoweather.data.entity.mapper;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

// TODO: make the tests text languagee-independent

public class WeatherEntityDataMapperTest {

    WeatherEntityDataMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new WeatherEntityDataMapper();
    }

    @Test
    public void testTransformWindDirectionNorth() throws Exception {
        String direction = mapper.transformWindDirection(0);
        Assert.assertEquals("North", direction);
    }

    @Test
    public void testTransformWindDirectionNorthUpper() throws Exception {
        String direction = mapper.transformWindDirection(22);
        Assert.assertEquals("North", direction);
    }

    @Test
    public void testTransformWindDirectionNorthLower() throws Exception {
        String direction = mapper.transformWindDirection(338);
        Assert.assertEquals("North", direction);
    }

    @Test
    public void testTransformWindDirectionNortheast() throws Exception {
        String direction = mapper.transformWindDirection(23);
        Assert.assertEquals("Northeast", direction);
    }

    @Test
    public void testTransformWindDirectionNortheastUpper() throws Exception {
        String direction = mapper.transformWindDirection(67);
        Assert.assertEquals("Northeast", direction);
    }

    @Test
    public void testTransformWindDirectionWest() throws Exception {
        String direction = mapper.transformWindDirection(292);
        Assert.assertEquals("West", direction);
    }
}