package sudo.configrator.services;

import java.util.List;

import sudo.configrator.dtos.ListViewDTO;
import sudo.configrator.entities.ListViewInfo;
import sudo.dtos.ResultMapper;

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
