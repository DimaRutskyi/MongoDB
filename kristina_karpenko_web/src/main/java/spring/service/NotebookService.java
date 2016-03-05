package spring.service;

import spring.domain.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Kris on 15.02.2016.
 */
public interface NotebookService {

    Long receive(Long noteId, int amount, double price);
    Long sale(Long storeId, int amount);
    boolean updateCPU(CPU cpu);
    boolean updateMemory(Memory memory);
    boolean updateVendor(Vendor vendor);
    boolean updateNotebook(Notebook notebook);
    boolean removeFromStore(Store store, int amount);
    List getNotebooksByPortion(int size);
    List getNotebooksGtAmount(int amount);
    List getNotebooksByCpuVendor(Vendor cpuVendor);
    List getNotebooksFromStore();
    List getNotebooksStorePresent();
    Map getSalesByDays();
}
