package shop.main.data.service;

import java.util.List;

import shop.main.data.objects.Block;
import shop.main.data.objects.MenuItem;

public interface MenuItemService {
void saveMenuItem(MenuItem item);
void deleteMenuItem(MenuItem item);
List<MenuItem> listAll();
MenuItem findMenuItemById(long id);	
void deleteMenuItemById(long id);
}
