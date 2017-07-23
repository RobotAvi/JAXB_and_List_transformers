package com.britebill.interview.transformers;

import org.junit.Assert;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.*;

public class TransformerDataTest {

    @Test
    public void testNullInput() {
        //Given
        List<String> data = new ArrayList<>();
        SecureRandom random = new SecureRandom();
        int initialElementsCount = random.ints(10, 100).findFirst().orElse(0);
        int nullElementCount = initialElementsCount / 3;
        for (int i = 0; i < nullElementCount; i++) {
            data.add(i, null);
        }
        for (int i = nullElementCount; i < initialElementsCount; i++) {
            data.add(i, UUID.randomUUID().toString());
        }
        TransformerData transformerData = new TransformerData();
        //When
        List<String> transformedData = transformerData.transformData(data);
        //Then
        assert nullElementCount == initialElementsCount - transformedData.size();
    }

    @Test
    public void testEmptyInput() {
        //Given
        List<String> data = new LinkedList<>();
        TransformerData transformerData = new TransformerData();
        //When
        List<String> transformedData = transformerData.transformData(data);
        //Then
        assert data.size() == transformedData.size();
    }

    @Test
    public void testDuplicates() {
        //Given
        SecureRandom random = new SecureRandom();
        int initialElementsCount = random.ints(10, 100).findFirst().orElse(0);
        List<String> data = new ArrayList<>(initialElementsCount);
        int distinctElementsCount = initialElementsCount / 3;
        for (int i = 0; i < distinctElementsCount; i++) {
            data.add(i, UUID.randomUUID().toString());
        }
        for (int i = distinctElementsCount; i < initialElementsCount; i++) {
            data.add(i, data.get(random.ints(0, distinctElementsCount - 1).findFirst().orElse(0)));
        }
        TransformerData transformerData = new TransformerData();
        //When
        List<String> transformedData = transformerData.transformData(data);
        //Then
        assert distinctElementsCount == transformedData.size();
    }

    @Test
    public void testSorting() {
        //Given

        SecureRandom random = new SecureRandom();
        int initialElementsCount = random.ints(10, 100).findFirst().orElse(0);
        List<String> initialData = new ArrayList<>(initialElementsCount);
        initialData.add("#");
        for (int i = 1; i < initialElementsCount; i++) {
            initialData.add("#" + initialData.get(i - 1));
        }
        List<String> reversedData = new ArrayList<>(initialData);
        reversedData.sort(Comparator.reverseOrder());
        TransformerData transformerData = new TransformerData();
        //When
        List<String> transformedData = transformerData.transformData(reversedData);
        //Then
        Assert.assertEquals(initialData, transformedData);
        Assert.assertNotEquals(reversedData, transformedData);
    }
}
