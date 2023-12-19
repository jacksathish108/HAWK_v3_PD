package hawk.configrator.services;

import java.util.List;

import hawk.configrator.dtos.ListViewDTO;
import hawk.configrator.entities.ListViewInfo;
import hawk.dtos.ResultMapper;

public interface ListViewService {
	ResultMapper setListView(ListViewDTO viewDTO);

	ResultMapper getListView();

	ResultMapper getListViewByid(Long id);

	ListViewInfo getListViewInfoByid(Long id);

	ListViewInfo getListViewInfoByCode(String code);

	ResultMapper deleteListView(Long id);

	ResultMapper getListViewByCode(String linkCode);
	List<ListViewDTO>  getListViewBySelectedQtags(List qTags);
	ResultMapper getAllSelectQtags();
	

}
