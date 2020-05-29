package com.esliceu.core.utils;

import net.jcip.annotations.NotThreadSafe;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
@Service
@NotThreadSafe
public class HashService {
    private final Set<String> hashes = new HashSet<String>();

    public String getHash(String grup){
        String hash = this.randomHash() + "_" + grup; //GENERATOR
        hashes.add(hash);
        return hash;
    }

    public void invalidateHash(String hash){
        hashes.remove(hash);
    }

    public boolean checkHash(String hash) {
        return hashes.contains(hash);
    }

    private String randomHash(){
        int size = 15;
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}
