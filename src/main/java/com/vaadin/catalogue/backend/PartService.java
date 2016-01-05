package com.vaadin.catalogue.backend;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// backend class
public class PartService {

    private static PartService instance; //create an instance of partservice class

    public static PartService createService() {
        return instance = new PartService(); //constructs partservices instance
    }

    private HashMap<Long, Part> parts = new HashMap<>();
    private long nextId = 0;

    public synchronized List<Part> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (Part part : parts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || part.toString().toLowerCase()
                                .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(part.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(PartService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Part>() {

            @Override
            public int compare(Part o1, Part o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized long count() {
        return parts.size();
    }

    public synchronized void delete(Part value) {
        parts.remove(value.getId());
    }

    public synchronized void save(Part entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Part) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        parts.put(entry.getId(), entry);
    }
    

}
