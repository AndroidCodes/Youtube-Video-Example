package in.gajerait.liveena.utils;

import android.graphics.Bitmap;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class MemoryCache {
    private HashMap<String, SoftReference<Bitmap>> cache;

    public MemoryCache() {
        this.cache = new HashMap();
    }

    public Bitmap get(String id) {
        if (this.cache.containsKey(id)) {
            return (Bitmap) ((SoftReference) this.cache.get(id)).get();
        }
        return null;
    }

    public void put(String id, Bitmap bitmap) {
        this.cache.put(id, new SoftReference(bitmap));
    }

    public void clear() {
        this.cache.clear();
    }
}
