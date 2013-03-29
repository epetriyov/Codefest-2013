package com.petriyov.android.libs.sparsearrays;

import java.io.IOException;
import java.io.Serializable;

import android.util.SparseIntArray;

public class SerializableSparseIntArrayContainer implements Serializable {

    private static final long serialVersionUID = 393662066105575556L;

    private SparseIntArray mSparseArray;

    public SerializableSparseIntArrayContainer(SparseIntArray mDataArray) {
        this.mSparseArray = mDataArray;
    }

    public SparseIntArray getSparseArray() {
        return mSparseArray;
    }

    public void setSparseArray(SparseIntArray sparseArray) {
        this.mSparseArray = sparseArray;
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException,
            ClassNotFoundException {
        long readSerialVersion = in.readLong();
        if (readSerialVersion != serialVersionUID) {
            throw new IOException("serial version mismatch");
        }
        int sparseArraySize = in.read();
        mSparseArray = new SparseIntArray(sparseArraySize);
        for (int i = 0; i < sparseArraySize; i++) {
            int key = in.readInt();
            int value = in.readInt();
            mSparseArray.put(key, value);
        }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.writeLong(serialVersionUID);
        int sparseArraySize = mSparseArray.size();
        out.write(sparseArraySize);
        for (int i = 0; i < sparseArraySize; i++) {
            int key = mSparseArray.keyAt(i);
            out.writeInt(key);
            int value = mSparseArray.get(key);
            out.writeInt(value);
        }
    }

}