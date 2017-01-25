package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.MenuItem;

public interface MenuItemService {
void save(MenuItem item);
void delete(MenuItem item);
List<MenuItem> listAll();
MenuItem findById(long id);	
void deleteById(long id);
}
