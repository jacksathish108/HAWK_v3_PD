package sudo.product.services;

import java.util.List;

import sudo.product.dtos.ListViewDTO;
import sudo.product.entities.ListViewInfo;
import sudo.user.dtos.ResultMapper;

public interface ListViewService {
	ResultMapper getListView();
	ListViewInfo getListViewInfoByid(Long id);
	ListViewInfo getListViewInfoByCode(String code);
	ResultMapper getListViewByCode(String linkCode);
	List<ListViewDTO>  getListViewBySelectedQtags(List qTags);
	ResultMapper getAllSelectQtags();
	

}
