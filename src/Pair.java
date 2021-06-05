public class Pair<K, V> {
    K fst;
    V snd;

    public Pair(K fst, V snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public int hashCode() {
        long bits = (fst == null) ? 0 : fst.hashCode();
        bits = bits * 31 + ((snd == null) ? 0 : snd.hashCode());
        return ((int) bits ^ (int) (bits >> 32));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj instanceof Pair<?, ?>) {
            Pair<?, ?> pair = (Pair<?, ?>) obj;
            return fst.equals(pair.fst) && snd.equals(pair.snd);
        }
        return false;
    }

    @Override
    public String toString() {
        return fst + "=" + snd;
    }
}