package com.example.sensorapp;

//Ebenfalls aus dem Codebeispiel Ordner von Moodle
public class Smoother {
    int pos = 0;
    int size = 10;
    double sum = 0;
    double[] buffer = new double[size];


     //von Gemini, weil die App im Emulator immer abstürzt. Am richtigen Handy geht es aber auch ohne
    public Smoother() {
        for(int i=0; i<size; i++) buffer[i] = 0;
    }

    double smooth(double value) {
        sum += value;
        sum -= buffer[pos];
        buffer[pos] = value;
        pos++;
        if (pos >= size)
            pos = 0;
        return sum / (double) size;
    }
}
