package uk.org.indra.util;

public class ArrayFinder {
	private byte[] bytesToSearch;
	private byte[] bytesToFind;
	private int cursor = 0;

	public ArrayFinder(byte[] bytesToSearch, byte[] bytesToFind) {
		this.bytesToSearch = bytesToSearch;
		this.bytesToFind = bytesToFind;
		if (bytesToFind.length == 0 || bytesToSearch.length == 0) {
			// Don't bother much
			cursor = -1;
		}
	}

	public int nextMatchingIndex() {
		if (cursor != -1) {

			// Statistically we will be testing for the first element of the toFind array most often
			// so we save many array lookup operations by extracting it here. 
			byte firstByteToFind = bytesToFind[0];
			int lastPossibleMatch = bytesToSearch.length - bytesToFind.length;

			// Labelling this outer loop allows us to bail out of the inner loop (using continue) as soon as we find a mismatch.
			findfirst:
				for (; cursor <= lastPossibleMatch; cursor++) {

					if (bytesToSearch[cursor] == firstByteToFind) {

						// Try to find the rest of bytesToFind
						for (int toFindCursor = 1; toFindCursor < bytesToFind.length; toFindCursor++) {
							if (bytesToSearch[cursor + toFindCursor] != bytesToFind[toFindCursor]) {
								// Got mismatch somewhere, so resume search for first byte
								continue findfirst;
							}
						}

						// Got right to the end of bytesToFind without a mismatch. So we found it!
						// Return index of match, then inc cursor ready for next search
						int foundAt = cursor;
						cursor += bytesToFind.length;
						return foundAt;
					}

				}
			// Got through whole array without finding a match
			cursor = -1;
		}
		return cursor;
	}


}
