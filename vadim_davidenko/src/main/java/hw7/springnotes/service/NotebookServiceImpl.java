package hw7.springnotes.service;

import hw7.springnotes.dao.*;
import hw7.springnotes.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Вадим on 14.02.2016.
 */

@Scope("singleton")
@Service("notebookService")
@Transactional
public class NotebookServiceImpl implements NotebookService {

    @Autowired (required = true)
    private NotebookDao notebookDao;

    @Autowired (required = true)
    private VendorDao vendorDao;

    @Autowired (required = true)
    private CPUDao cpuDao;

    @Autowired (required = true)
    private MemoryDao memoryDao;

    @Autowired (required = true)
    private StoreDao storeDao;

    @Autowired (required = true)
    private SalesDao salesDao;

    private static int pageCounter = -1;

    private NotebookServiceImpl() {}

    ////////////////////////////////////////////////////////////
    // Inserts

    public Long insertCPU(CPU cpu) {
        if (cpu == null) return 0L;
        return cpuDao.create(cpu);
    }

    public Long insertMemory(Memory memory) {
        if (memory == null) return 0L;
        return memoryDao.create(memory);
    }

    public Long insertVendor(Vendor vendor) {
        if (vendor == null) return 0L;
        return vendorDao.create(vendor);
    }

    public Long insertNotebook(Notebook notebook) {
        if (notebook == null) return 0L;
        return notebookDao.create(notebook);
    }

    ////////////////////////////////////////////////////////////
    // Updates

    public boolean updateCPU(CPU cpu) {
        return cpuDao.update(cpu);
    }

    public boolean updateMemory(Memory memory) {
        return memoryDao.update(memory);
    }

    public boolean updateVendor(Vendor vendor) {
        return vendorDao.update(vendor);
    }

    public boolean updateNotebook(Notebook notebook) {
        return notebookDao.update(notebook);
    }

    ////////////////////////////////////////////////////////////
    // Store services

    public Long receive(Long noteId, int amount, double price) {
        Store store = new Store();
        store.setAmount(amount);
        store.setPrice(price);
        Notebook note = getNotebookById(noteId);
        store.setNotebook(note);

        return storeDao.create(store);
    }

    public boolean removeFromStore(Store store, int amount) {
        Integer currentAmount = store.getAmount();

        if (currentAmount.compareTo(amount) >= 0) {
            Integer newAmount = currentAmount - amount;
            Double newPrice = store.getPrice() / currentAmount * newAmount;
            store.setAmount(newAmount);
            store.setPrice(newPrice);

            return storeDao.update(store);
        }
        return false;
    }

    public Long sale(Long storeId, int amount) {
        Store store = getStoreById(storeId);
        if (store == null) return 0L;

        Integer currentAmount = store.getAmount();
        if (currentAmount.compareTo(amount) >= 0) {
            Sales sale = new Sales();
            sale.setAmount(amount);
            sale.setStore(store);
            sale.setDate(new Date());
            return salesDao.create(sale);
        } else {
            return 0L;
        }
    }

    /////////////////////////////////////////////////////////////
    // Reports

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Notebook> getNotebooksByPortion(int size) {
        if (size == 0) {
            pageCounter = -1;
            return null;
        } if (size < 0) {
            if (pageCounter > 0) pageCounter--;
        } else {
            pageCounter++;
        }
        return (List<Notebook>) notebookDao.findByPortion(pageCounter, Math.abs(size));
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Notebook> getNotebooksGtAmount(int amount) {
        return (List<Notebook>) notebookDao.findGtAmount(amount);
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Notebook> getNotebooksByCpuVendor(Vendor cpuVendor) {
        return (List<Notebook>) notebookDao.findByCpuVendor(cpuVendor);
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Notebook> getNotebooksFromStore() {
        return (List<Notebook>) notebookDao.findAllOnStore();
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Store> getNotebooksStorePresent() {
        return (List<Store>) storeDao.findOnStorePresent();
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public Map<Date, Integer> getSalesByDays() {
        return (Map<Date, Integer>) salesDao.findAllByDays();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters by Id

    @Transactional (readOnly = true)
    public Notebook getNotebookById(Long id) {
        return notebookDao.read(id);
    }

    @Transactional (readOnly = true)
    public Vendor getVendorById(Long id) {
        return vendorDao.read(id);
    }

    @Transactional (readOnly = true)
    public CPU getCPUById(Long id) {
        return cpuDao.read(id);
    }

    @Transactional (readOnly = true)
    public Memory getMemoryById(Long id) {
        return memoryDao.read(id);
    }

    @Transactional (readOnly = true)
    public Store getStoreById(Long id) {
        return storeDao.read(id);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Get all (list)

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Notebook> getAllNotebooks() {
        return notebookDao.findAll();
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Vendor> getAllVendors() {
        return vendorDao.findAll();
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<CPU> getAllCPUs() {
        return cpuDao.findAll();
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Memory> getAllMemories() {
        return memoryDao.findAll();
    }

    @Transactional (readOnly = true)
    @SuppressWarnings("unchecked")
    public List<Store> getAllStores() {
        return storeDao.findAll();
    }
}
