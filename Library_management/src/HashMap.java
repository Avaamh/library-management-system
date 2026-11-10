
class HashMap<String, User> {

    private Entry<String, User>[] table;   //Buckets
    private int capacity = 10;  //Initial count of buckets

    static class Entry<String, User> {
        String key;  // id
        User value;  // user
        Entry<String, User> next;

        public Entry(String key, User value, Entry<String, User> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public HashMap() {
        table = new Entry[capacity];
    }

    public void put(String newKey, User data) {
        if (newKey == null)
            return;

        int hash = hash(newKey);
        Entry<String, User> newEntry = new Entry<String, User>(newKey, data, null);

        //if table location does not contain any entry, stores entry there.
        if (table[hash] == null) {
            table[hash] = newEntry;
        } else {
            Entry<String, User> previous = null;
            Entry<String, User> current = table[hash];

            while (current != null) { // last entry of bucket.
                if (current.key.equals(newKey)) {
                    if (previous == null) {  // node will be inserted on first of bucket.
                        newEntry.next = current.next;
                        table[hash] = newEntry;
                        return;
                    } else {
                        newEntry.next = current.next;
                        previous.next = newEntry;
                        return;
                    }
                }
                previous = current;
                current = current.next;
            }
            previous.next = newEntry;
        }
    }

    public User get(String key) {
        int hash = hash(key);
        if (table[hash] == null) {
            return null;
        } else {
            Entry<String, User> temp = table[hash];
            while (temp != null) {
                if (temp.key.equals(key))
                    return temp.value;
                temp = temp.next; //return the value of key.
            }
            return null;   //returns null if key is not found.
        }
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }

}