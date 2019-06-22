package cn.wwq.pojo;

import java.util.List;

/**
 * 分页参数
 * @param <T>
 */
public class PageBean<T> {

    private Integer pageNum;//当前页码
    private Integer pageSize; //每页显示条数
    private Integer totalCount;  //总条数
    private Integer totalPage;  //总页数
    private List<T> pageList; //分页数据

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }
}
