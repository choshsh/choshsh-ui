package com.doms.api.svc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doms.api.dto.DiDTO;
import com.doms.api.dto.DiSearch;
import com.doms.api.dto.DieDTO;
import com.doms.api.repo.DiRepo;

@Service
public class DiSvc {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private DiRepo diRepo;

	public List<DiDTO> getDiList(DiSearch diSearch) throws Exception {
		return diRepo.getDiList(diSearch);
	}

	public List<DieDTO> getDieList() throws Exception {
		return diRepo.getDieList();
	}

	public int saveDi(DiDTO diDTO) {
		int result = 0;
		try {
			if (diRepo.getDiCount(diDTO) > 0) {
				/* 마스터 등록 */
				int diResult = diRepo.updateDi(diDTO);
				/* 자식 등록 */
				if (diResult > 0 && (diDTO.getDiIsError().equalsIgnoreCase("Y")
						|| diDTO.getDiIsError2().equalsIgnoreCase("Y"))) {
					this.delDie(diDTO);
					this.saveDie(diDTO.getDieList());
				} else {
					this.delDie(diDTO);
				}
				result = 1;
			} else {
				/* 마스터 등록 */
				int diResult = diRepo.insertDi(diDTO);
				/* 자식 등록 */
				if (diResult > 0
						&& (diDTO.getDiIsError().equalsIgnoreCase("Y") || diDTO.getDiIsError2().equalsIgnoreCase("Y")))
					this.saveDie(diDTO.getDieList());
				result = 1;
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			result = 0;
		}
		return result;
	}

	private void saveDie(List<DieDTO> dieDTO) throws Exception {
		dieDTO.forEach(o -> {
			diRepo.insertDie(o);
		});
	}

	private void delDie(DiDTO diDTO) throws Exception {
		diRepo.deleteDie(diDTO);
	}

	public int getMnYn(String diDate) {
		return diRepo.getMnYn(diDate);
	}

	public int getAftnYn(String diDate) {
		return diRepo.getAftnYn(diDate);
	}

}
