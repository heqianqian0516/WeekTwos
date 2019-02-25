package bean;

import java.util.List;

public class ResultBean {
    private List<RxxpBean> rxxp;
    private List<RxxpBean> pzsh;
    private List<RxxpBean> mlss;

    public List<RxxpBean> getRxxp() {
        return rxxp;
    }

    public void setRxxp(List<RxxpBean> rxxp) {
        this.rxxp = rxxp;
    }

    public List<RxxpBean> getPzsh() {
        return pzsh;
    }

    public void setPzsh(List<RxxpBean> pzsh) {
        this.pzsh = pzsh;
    }

    public List<RxxpBean> getMlss() {
        return mlss;
    }

    public void setMlss(List<RxxpBean> mlss) {
        this.mlss = mlss;
    }
}
