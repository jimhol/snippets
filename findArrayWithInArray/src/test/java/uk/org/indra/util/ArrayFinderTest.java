package uk.org.indra.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ArrayFinderTest
{
	 
    @Test
    public void identical() throws Exception
    {
        byte[] matchingBytes = {0x43};
        byte[] bytes = matchingBytes;
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void returnMinusOneIfNoMatchSingle() throws Exception
    {
        byte[] matchingBytes = {0x1b};
        byte[] bytes = {0x43};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void returnMinusOneIfNoMatch() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43, 0x04};
        byte[] bytes = {0x1b, 0x43, 0x03};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void matchForOneByte() throws Exception
    {
        byte[] matchingBytes = {0x43};
        byte[] bytes = {0x1b, 0x43, 0x04};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(1, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void matchForOneByteMultiple() throws Exception
    {
        byte[] matchingBytes = {0x43};
        byte[] bytes = {0x43, 0x43, 0x43};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(1, ArrayFinder.nextMatchingIndex());
        assertEquals(2, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void matchForOneByteMultipleWithTail() throws Exception
    {
        byte[] matchingBytes = {0x43};
        byte[] bytes = {0x43, 0x43, 0x43, 0x00, 0x43, 0x00};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(1, ArrayFinder.nextMatchingIndex());
        assertEquals(2, ArrayFinder.nextMatchingIndex());
        assertEquals(4, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void NoMatchForTwoBytes() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x04};
        byte[] bytes = {0x1b, 0x43, 0x04};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void MatchForTwoBytes() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43};
        byte[] bytes = {0x1b, 0x43, 0x04};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void MatchForTwoBytesMultiple() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43};
        byte[] bytes = {0x1b, 0x43, 0x1b, 0x43, 0x1b, 0x43};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(2, ArrayFinder.nextMatchingIndex());
        assertEquals(4, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void MatchForTwoBytesMultipleWithTail() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43};
        byte[] bytes = {0x1b, 0x43, 0x1b, 0x43, 0x1b, 0x43, 0x1b};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(2, ArrayFinder.nextMatchingIndex());
        assertEquals(4, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void trickyPair() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43};
        byte[] bytes = {0x00, 0x1b, 0x43, 0x1b, 0x1b, 0x43, 0x1b, 0x43, 0x1b};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(1, ArrayFinder.nextMatchingIndex());
        assertEquals(4, ArrayFinder.nextMatchingIndex());
        assertEquals(6, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void oneMatchOneNoMatch() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43};
        byte[] bytes = {0x1b, 0x43, 0x04};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void twoMatchesOneNoMatch() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43};
        byte[] bytes = {0x1b, 0x43, 0x04, 0x1b, 0x43};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(3, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void twoMatchesOneNoMatchAndMatchingBytesHasDuplicateBytes() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43, 0x1b};
        byte[] bytes = {0x1b, 0x43, 0x1b, 0x04, 0x1b, 0x43, 0x1b};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(4, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void twoMatchesOneNoMatchAndMatchingContainsPartialMatchInTheSecondOne() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43, 0x1b};
        byte[] bytes = {0x1b, 0x43, 0x1b, 0x1b, 0x43, 0x04, 0x1b, 0x43, 0x1b};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(6, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void twoMatchesOneNoMatchAndMatchingContainsPartialMatchAtEnd() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43, 0x1b};
        byte[] bytes = {0x1b, 0x43, 0x1b, 0x43, 0x1b, 0x43, 0x1b, 0x1b, 0x43};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(4, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    
    @Test
    public void twoMatchesOneNoMatchAndMatchingContainsPartialMatchInTheSecondOn() throws Exception
    {
        byte[] matchingBytes = {0x01, 0x02, 0x03};
        byte[] bytes = {0x01, 0x02, 0x03, 0x01, 0x01, 0x02, 0x01, 0x02, 0x03, 0x1b};
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(6, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void bigArrayToSearchButNotFound() throws Exception
    {
        byte[] matchingBytes = {0x1b, 0x43, 0x1b};
        byte[] bytes = new byte[103027];
        bytes[103024] = 0x1b;
        bytes[103025] = 0x43;
        bytes[103026] = 0x1c;
       
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void bigIdenticalArraysToFindAndSearch() throws Exception
    {
        byte[] matchingBytes = new byte[103027];
        matchingBytes[103026] = 0x01;
        byte[] bytes = matchingBytes;
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(0, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void bigArrayToSearchFoundAtEnd() throws Exception
    {
        byte[] matchingBytes = {0x01, 0x02, 0x03};
        byte[] bytes = new byte[103027];
        bytes[103022] = 0x01;
        bytes[103023] = 0x02;
        bytes[103024] = 0x01;
        bytes[103025] = 0x02;
        bytes[103026] = 0x03;
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);

        assertEquals(103024, ArrayFinder.nextMatchingIndex());
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }
    
    @Test
    public void biggerArrayToFindThanToSearch() throws Exception
    {
        byte[] matchingBytes = new byte[2];
        byte[] bytes = new byte[1];
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void emptyArrayToFind() throws Exception
    {
        byte[] matchingBytes = new byte[0];
        byte[] bytes = new byte[1];
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void emptyArrayToSearch() throws Exception
    {
        byte[] matchingBytes = new byte[1];
        byte[] bytes = new byte[0];
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }

    @Test
    public void emptyBoth() throws Exception
    {
        byte[] matchingBytes = new byte[0];
        byte[] bytes = new byte[0];
        
        ArrayFinder ArrayFinder = new ArrayFinder(bytes, matchingBytes);
        
        assertEquals(-1, ArrayFinder.nextMatchingIndex());
    }


}
