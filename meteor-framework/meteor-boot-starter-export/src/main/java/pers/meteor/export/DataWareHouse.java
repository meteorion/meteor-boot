package pers.meteor.export;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 数据仓库
 * @author 钟宗兵
 * @since 1.0.0
 */
public class DataWareHouse {
    /**
     * 默认每个数据盒大小，即单文件条目数
     */
    private static final int DEFAULT_DATA_BOX_SIZE = 10_0000;

    private final CopyOnWriteArrayList<DataBox<T>> dataBoxes;
    private final int boxSize;

    public DataWareHouse() {
        this.boxSize = DEFAULT_DATA_BOX_SIZE;
        this.dataBoxes = new CopyOnWriteArrayList<>();
        this.dataBoxes.add(new DataBox<>(this.boxSize));
    }

    public DataWareHouse(int boxSize) {
        this.boxSize = boxSize;
        this.dataBoxes = new CopyOnWriteArrayList<>();
        this.dataBoxes.add(new DataBox<>(this.boxSize));
    }

    public void add(List<T> data) {
        DataBox<T> dataBox = dataBoxes.get(0);
        int freeSize = dataBox.getFreeSize();
        if (freeSize <= 0) {
            dataBoxes.add(new DataBox<>(this.boxSize));
            add(data);
        } else if (data.size() > freeSize){
            dataBox.add(data.subList(0, freeSize));
            add(data.subList(freeSize, data.size()));
        } else {
            dataBox.add(data);
        }
    }

    /**
     * 获取完整的数据盒子
     *
     * @return /
     */
    public List<T> getFullDataBox() {
        if (dataBoxes.size() <= 1) {
            return new ArrayList<>();
        }
        return dataBoxes.get(dataBoxes.size() - 1).getData();
    }

    public static class DataBox<T> {
        /**
         * 数据盒子大小
         */
        private final int boxSize;
        /**
         * 盒子数据
         */
        private final List<T> data;

        public DataBox(int boxSize) {
            this.boxSize = boxSize;
            this.data = new ArrayList<T>(boxSize * 3/2);
        }

        public int add(List<T> data) {
            if (getFreeSize() < data.size()) {
                throw new IllegalStateException("There is not enough space left");
            }
            this.data.addAll(data);
            return getCurrentBoxSize();
        }

        public int getCurrentBoxSize() {
            return this.data.size();
        }

        public int getFreeSize() {
            return this.boxSize - getCurrentBoxSize();
        }

        public List<T> getData() {
            return this.data;
        }
    }

}
