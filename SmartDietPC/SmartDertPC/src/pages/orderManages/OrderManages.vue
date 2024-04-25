<template>
  <div class="order">
    <!-- 订单管理 -->
    <div class="formData">
      <el-card class="searchModule">
        <el-form :model="order" :inline="true" class="top" ref="order">
          <el-form-item label="客户名称" prop="nickName" class="nickName">
            <el-input v-model="order.nickName" clearable class="text" placeholder="请输入内容"></el-input>
          </el-form-item>

          <!-- 订单状态 -->
          <el-form-item label="订单状态" prop="status" class="status">
            <el-select v-model="order.status" placeholder="请选择">
              <el-option
                v-for="(item,index) in option"
                :key="index"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-button type="primary" @click="search()" style="position:absolute;top:80px">查询</el-button>
          <el-button
            type="primary"
            @click="resetForm('order')"
            style="position:fixed;top:150px;right:390px;"
          >重置</el-button>

          <div class="time">
            <div class="block">
              <el-form-item label="空箱下单时间" prop="emptyBoxOrderTime" class="time">
                <el-date-picker
                  v-model="order.emptyBoxOrderTime"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  value-format="yyyy-MM-dd"
                  :default-time="['00:00:00', '23:59:59']"
                ></el-date-picker>
              </el-form-item>
            </div>

            <div class="blocka">
              <el-form-item label="重箱下单时间" prop="heavyBoxOrderTime" class="time">
                <el-date-picker
                  v-model="order.heavyBoxOrderTime"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  value-format="yyyy-MM-dd"
                  :default-time="['00:00:00', '23:59:59']"
                ></el-date-picker>
              </el-form-item>
            </div>
          </div>
        </el-form>
      </el-card>
    </div>

    <!-- 按钮 + SiruiTable组件 -->
    <div class="main">
      <div class="allBt">
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <el-button type="primary" @click="detail">详情</el-button>
        <el-button type="primary" @click="delData()">删除</el-button>
      </div>

      <div class="tableData">
        <sirui-table
          class="operateTable"
          :page="page"
          :loading="load"
          :data="tableData"
          @handleSelectionChange="handleSelectionChange"
          @handleSizeChange="handleSizeChange"
          @handleCurrentChange="handleCurrentChange"
          height="100"
          :tableHeight="tableHeight"
        >
          <el-table-column align="center" type="selection" show-overflow-tooltip></el-table-column>

          <el-table-column prop="orderCode" label="订单编号" show-overflow-tooltip></el-table-column>
          <el-table-column prop="sta" label="订单状态" show-overflow-tooltip></el-table-column>
          <el-table-column prop="orderName" label="订单名称" show-overflow-tooltip></el-table-column>
          <el-table-column prop="nickName" label="客户名称" show-overflow-tooltip></el-table-column>
          <el-table-column prop="boxId" label="箱子id" show-overflow-tooltip></el-table-column>
          <el-table-column prop="boxCode" label="箱子编号" show-overflow-tooltip></el-table-column>
          <el-table-column prop="boxStandard" label="箱子规格" show-overflow-tooltip></el-table-column>
          <el-table-column prop="totalPrice" label="所需积分" show-overflow-tooltip></el-table-column>
          <el-table-column prop="leaseDuration" label="租赁时长" show-overflow-tooltip></el-table-column>
          <el-table-column prop="totalPrice" label="实付积分" show-overflow-tooltip></el-table-column>

          <!-- 操作 -->
          <el-table-column width="100" prop="btn" label="操作" fixed="right" show-overflow-tooltip>
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="operation(scope.row)"
                v-if="scope.row.status===1"
                style="color:orange;"
              >发送空箱</el-button>

              <el-button
                size="mini"
                v-else-if="scope.row.status===4"
                style="color:green;"
                @click="operation(scope.row)"
              >收取重箱</el-button>

              <el-button
                size="mini"
                v-else-if="scope.row.status===6"
                style="color:blue;"
                @click="operation(scope.row)"
              >发送重箱</el-button>

              <el-button
                size="mini"
                v-else-if="scope.row.status===9"
                style="color:pink;"
                @click="operation(scope.row)"
              >收取空箱</el-button>

              <el-button size="mini" v-else style="color:red;display:none;">已完成</el-button>
            </template>
          </el-table-column>

          <!-- 可显示隐藏
          table里的this.data=1的话 this.isShow=true
          如果订单状态为9 table里的this.data=9 this.isShow2=true-->

          <!-- 订单状态1-5 显示空箱上门下单时间 -->
          <!-- <el-table-column
            prop="emptyBoxCallTime"
            label="空箱上门下单时间"
            v-if="isShow1"
            :show-overflow-tooltip="true"
          ></el-table-column>-->

          <!-- 订单状态6-10 显示重箱提取下单时间 -->
          <!-- <el-table-column
            prop="heavyBoxCallTime"
            label="重箱提取下单时间"
            v-if="isShow2"
            :show-overflow-tooltip="true"
          ></el-table-column>-->

          <!-- 空箱上门下单时间 -->
          <el-table-column
            label="空箱上门下单时间"
            v-if="emptyShow"
            prop="emptyBoxCallTime"
            :show-overflow-tooltip="true"
          ></el-table-column>

          <!-- 重箱上门下单时间 -->
          <el-table-column
            label="重箱上门下单时间"
            prop="heavyBoxCallTime"
            v-if="heavyShow"
            :show-overflow-tooltip="true"
          ></el-table-column>
        </sirui-table>
      </div>
    </div>

    <OrderAdd ref="detail"></OrderAdd>
  </div>
</template>

<script>
import SiruiTable from "@/components/SiruiTable";
import OrderAdd from "@/pages/orderManages/OrderAdd";

export default {
  nickName: "orderManages",
  components: {
    SiruiTable,
    OrderAdd
  },
  data() {
    return {
      tableHeight: 400,
      //输入项数据
      order: {
        nickName: "",
        status: "",
        // payTime: "",
        emptyBoxOrderTime: ["", ""],
        heavyBoxOrderTime: ["", ""]
      },

      page: {
        pages: 0, //总共多少页
        pageNum: 1, //第几页
        pageSize: 10, //一页多少条数据
        total: 0 //总共多少条
      },
      // 缓冲的时间
      load: false,
      multipleSelection: [],
      // true 显示 false 隐藏
      // isShow1: false,
      // isShow2: false,
      heavyShow: true,
      emptyShow: true,

      id: "",
      version: "",
      operate: "",
      orderVO: {},

      //   订单状态
      option: [
        {
          value: "1",
          label: "待发空箱"
        },
        {
          value: "2",
          label: "已发空箱"
        },
        {
          value: "3",
          label: "送达空箱"
        },
        {
          value: "4",
          label: "待收重箱"
        },
        {
          value: "5",
          label: "已存储"
        },
        {
          value: "6",
          label: "待发重箱"
        },
        {
          value: "7",
          label: "已发重箱"
        },
        {
          value: "8",
          label: "送达重箱"
        },
        {
          value: "9",
          label: "待收空箱"
        },
        {
          value: "10",
          label: "已完成"
        }
      ],

      tableData: []
    };
  },

  mounted() {
    // 初始显示 所有数据
    this.getData();
  },

  methods: {
    // 查询列表数据
    getData() {
      this.$axios
        .get(
          "prod-api/backend/order/list?" +
            "pageNum=" +
            this.page.pageNum +
            "&pageSize=" +
            this.page.pageSize +
            "&nickName=" +
            this.order.nickName +
            "&status=" +
            this.order.status +
            "&emptyBoxOrderTimeStart=" +
            this.order.emptyBoxOrderTime[0] +
            "&emptyBoxOrderTimeEnd=" +
            this.order.emptyBoxOrderTime[1] +
            "&heavyBoxOrderTimeStart=" +
            this.order.heavyBoxOrderTime[0] +
            "&heavyBoxOrderTimeEnd=" +
            this.order.heavyBoxOrderTime[1]

          // this.order.payTime +
          // "&emptyBoxOrderTimeEnd=" +

          // this.order.payTime +
          // "&heavyBoxOrderTimeStart=" +
          // this.order.payTime +
          // "&heavyBoxOrderTimeEnd"
        )
        .then(res => {
          if (200 == res.data.code) {
            // 展示的数据
            this.tableData = res.data.rows;
            console.log(this.tableData);
            for (var i = 0; i < this.tableData.length; i++) {
              if (this.tableData[i].status == 1)
                this.tableData[i].sta = "待发空箱";
              if (this.tableData[i].status == 2)
                this.tableData[i].sta = "已发空箱";
              if (this.tableData[i].status == 3)
                this.tableData[i].sta = "送达空箱";
              if (this.tableData[i].status == 4)
                this.tableData[i].sta = "待收重箱";
              if (this.tableData[i].status == 5)
                this.tableData[i].sta = "已存储";
              if (this.tableData[i].status == 6)
                this.tableData[i].sta = "待发重箱";
              if (this.tableData[i].status == 7)
                this.tableData[i].sta = "已发重箱";
              if (this.tableData[i].status == 8)
                this.tableData[i].sta = "送达重箱";
              if (this.tableData[i].status == 9)
                this.tableData[i].sta = "待收空箱";
              if (this.tableData[i].status == 10)
                this.tableData[i].sta = "已完成";
            }
            // 分页
            this.page.total = res.data.total;
            this.$message({
              showClose: true,
              message: "查询成功",
              type: "success"
            });
          } else {
            this.$message({
              showClose: true,
              message: "查询失败",
              type: "error"
            });
          }
        });
    },

    sendNullk(val) {
      // console.log(val);
    },

    // 详情按钮
    detail() {
      // 为了防止子组件的值 影响父组件
      if (1 == this.multipleSelection.length) {
        this.$refs.detail.openDialog(
          true,
          JSON.parse(JSON.stringify(this.multipleSelection[0]))
        );
        localStorage.setItem("ids", this.multipleSelection[0].id);
      } else {
        this.$message({
          showClose: true,
          message: "请选择一条数据进行查看详情",
          type: "error"
        });
      }
    },

    //查询用户
    search() {
      this.getData();
    },

    //操作
    operation(state) {
      this.id = state.id;
      if (state.status == 1) {
        this.operate = 2;
      } else if (state.status == 2) {
        this.operate = 3;
      } else if (state.status == 3) {
        this.operate = 4;
      } else if (state.status == 4) {
        this.operate = 5;
      } else if (state.status == 5) {
        this.operate = 6;
      } else if (state.status == 6) {
        this.operate = 7;
      } else if (state.status == 7) {
        this.operate = 8;
      } else if (state.status == 8) {
        this.operate = 9;
      } else if (state.status == 9) {
        this.operate = 10;
      } else if (state.status == 10) {
        this.operate = 10;
      }
      let params = {};
      console.log(state.id, this.operate, state.version);
      this.$axios
        .put(
          "prod-api/backend/order/operate/" +
            state.id +
            "/" +
            this.operate +
            "/" +
            state.version,
          params
        )
        .then(res => {
          if (200 == res.data.code) {
            // 查询数据
            this.getData();
            this.$message({
              showClose: true,
              message: "查询成功",
              type: "success"
            });
          } else {
            this.$message({
              showClose: true,
              message: "res.data.msg",
              type: "error"
            });
          }
        });
    },

    //重置
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields();
      }
      this.getData();
    },

    //删除
    delData() {
      if (this.multipleSelection.length >= 1) {
        if (0 != this.multipleSelection.length) {
          this.$confirm("此操作将永久删除该数据，是否继续操作？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          })
            .then(() => {
              let params = {
                ids: []
              };
              for (var i = 0; i < this.multipleSelection.length; i++) {
                params.ids.push(this.multipleSelection[i].id);
              }
              params.ids = params.ids.join(",");

              this.$axios
                .delete("prod-api/backend/order/delete/" + params.ids)
                .then(res => {
                  if (200 == res.data.code) {
                    // 查询列表
                    this.getData();
                    this.$message({
                      type: "success",
                      message: res.data.msg
                    });
                    this.search();
                  } else {
                    this.$message({
                      type: "error",
                      message: res.data.msg
                    });
                    // 查询数据
                    this.getData();
                  }
                });
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消删除"
              });
            });
        } else {
          this.$message({
            showClose: true,
            message: "请选择数据进行操作",
            type: "error"
          });
        }
      } else {
        this.$message({
          showClose: true,
          message: "请选择数据进行操作",
          type: "error"
        });
      }
    },

    //选中触发事件
    handleSelectionChange(row) {
      this.multipleSelection = row;
    },

    //每条页数
    handleSizeChange(val) {
      this.page.pageSize = val;
      this.getData();
    },

    //第几页
    handleCurrentChange(val) {
      this.page.pageNum = val;
      this.getData();
    }
  }
};
</script>

<style lang="scss" scoped>
.order {
  position: relative;
  left: 50px;
}
.top {
  margin-left: 10px;
  // 客户名称
  .nickName {
    position: absolute;
    left: 30px;
  }

  // 订单状态
  .status {
    margin-right: 130px;
  }

  .time {
    display: flex;
    margin-top: 10px;
    .block {
      flex: 1.5;
    }
    .blocka {
      flex: 3;
    }
  }
  // 日期宽度
  .el-date-editor--daterange.el-input,
  .el-date-editor--daterange.el-input__inner,
  .el-date-editor--timerange.el-input,
  .el-date-editor--timerange.el-input__inner {
    width: 280px;
  }
}

.text {
  width: 200px;
  margin-right: 20px;
}
.gmtCreate {
  margin-top: 15px;
}
.el-form-item {
  margin-bottom: 0px;
}

.main {
  margin-top: 8px;
  background-color: #fff;
  height: 550px;
  .allBt {
    float: left;
    position: relative;
    height: 50px;
    top: 10px;
    .change {
      width: 130px;
    }
  }
}

.tableData {
  margin-left: 20px;
}
</style>