<template>
    <h3>用户角色管理</h3>
  <!--- 组织选择 --->
  <el-form :model="groupForm" label-width="120px">
    <el-form-item>
      <el-select v-model="groupForm.id" placeholder="请选择要查询的组织">
        <el-option
          v-for="group in groupList"
          :key="group.id"
          :label="group.name"
          :value="group.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-button @click="initByGroup()" type="primary">按组查询</el-button>
    </el-form-item>
  </el-form>
  <div class="button-container">
      <el-button @click="setUserRoleToBasic" type="success">设置用户角色为 basic</el-button>
      <el-button @click="addUserWithBasicRole" type="info">新增用户并设置为 basic 角色</el-button>
    </div>
  <el-row :gutter="20">
    <!------------用户列表---------------->
    <el-col :span="18">
      用户列表
      <hr />
      <el-table :data="memberList" stripe style="width: 100%">
        <el-table-column prop="username" label="用户编号" width="150" />
        <el-table-column prop="name" label="姓名" width="150" />
        <el-table-column prop="sex" label="性别" width="100" />
        <!--<el-table-column prop="job" label="身份" width="150" />-->
        <el-table-column prop="roles" label="身份/角色" />
        <!---------------操作栏-------------->
        <el-table-column label="操作">
          <template #default="scope">
            <!-------------授予角色对话窗-------------->
            <el-button size="small" @click="showGrantUserDialog(scope.$index)"
              >授予角色</el-button
            >
            <el-dialog
              v-model="grantUserDialogSeen[scope.$index]"
              title="授予角色"
              append-to-body
            >
              <el-table
                :data="roleList"
                highlight-current-row
                @current-change="changeCurrentGrantRole"
              >
                <el-table-column prop="roleName" label="角色名称" width="150" />
              </el-table>
              <div style="margin-top: 20px">
                <el-button @click="hideGrantUserDialog(scope.$index)"
                  >取消</el-button
                >
                <el-button
                  type="primary"
                  @click="confirmGrantUser(scope.$index, scope.row)"
                  >确认</el-button
                >
              </div>
            </el-dialog>

            <!-------------收回角色对话窗-------------->
            <el-button
              size="small"
              @click="showRevokeUserDialog(scope.$index, scope.row)"
              >收回角色</el-button
            >
            <el-dialog
              v-model="revokeUserDialogSeen[scope.$index]"
              title="收回角色"
              append-to-body
            >
              <el-table
                :data="ownRoleList"
                highlight-current-row
                @current-change="changeCurrentRevokeRole"
              >
                <el-table-column prop="name" label="角色名称" width="150" />
              </el-table>
              <div style="margin-top: 20px">
                <el-button @click="hideRevokeUserDialog(scope.$index)"
                  >取消</el-button
                >
                <el-button
                  type="primary"
                  @click="confirmRevokeUser(scope.$index, scope.row)"
                  >确认</el-button
                >
              </div>
            </el-dialog>
            <!----------------删除成员按钮----------------->
            <el-button
              size="small"
              type="danger"
              @click="confirmDeleteUser(scope.row)"
              v-if="memberManageSeen"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
    </el-col>
    <!----------------邀请成员------------------------->
    <el-button v-if="memberManageSeen" @click="showInviteMemberDialog()">邀请成员</el-button>

    <el-dialog v-model="inviteMemberDialogSeen"
              title="邀请成员"
              append-to-body>
      邀请成员：
      <hr />
      用户姓名：<el-input v-model="inviteMemberNameInput" />
      <el-button @click="findUserByname()">按姓名搜索</el-button>
      <el-table :data="inviteMemberSearchUserList" stripe style="width: 100%">
        <el-table-column prop="username" label="用户编号" width="150" />
        <el-table-column prop="name" label="姓名" width="150" />
        <el-table-column prop="sex" label="性别" width="100" />
        <!--<el-table-column prop="job" label="身份" width="150" />-->
        <el-table-column prop="roles" label="身份/角色" />
        <el-table-column label="操作">
          <template #default="scope">
            <el-button @click="inviteUser(scope.row.username)">邀请</el-button>
          </template>
        </el-table-column>
      </el-table>
      按用户编号邀请：<el-input v-model="inviteMemberUsernameInput"></el-input>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="hideInviteMemberDialog()">取消</el-button>
          <el-button type="primary" @click="confirmInviteMemberDialog()"
            >确认</el-button
          >
        </span>
      </template>
    </el-dialog>

    <!-------------------角色列表---------------------->
    <el-col :span="6">
      角色列表
      <hr />
      <el-table :data="roleList" stripe style="width: 100%">
        <el-table-column prop="roleName" label="角色名称" width="150" />
        <el-table-column label="操作">
          <template #default="scope">
            <!----------- 修改角色--------------->
            <el-button
              size="small"
              @click="showEditRoleDialog(scope.$index, scope.row)"
              v-if="memberManageSeen"
              >修改</el-button
            >
            <el-dialog
              v-model="editRoleDialogSeen[scope.$index]"
              title="修改角色"
              append-to-body
            >
              <el-form :model="editRoleForm">
                <el-form-item label="角色名称">
                  <el-input v-model="editRoleForm.name" autocomplete="off" />
                </el-form-item>
              </el-form>
              <template #footer>
                <span class="dialog-footer">
                  <el-button @click="hideEditRoleDialog(scope.$index)"
                    >取消</el-button
                  >
                  <el-button
                    type="primary"
                    @click="confirmEditRole(scope.$index)"
                    >确认</el-button
                  >
                </span>
              </template>
            </el-dialog>
            <!---删除角色--->
            <el-button
              size="small"
              type="danger"
              v-if="memberManageSeen"
              @click="confirmDeleteRole(scope.row)"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!----------- 新增角色--------------->
      <el-button v-if="memberManageSeen" @click="showAddRoleDialog()"
        >新增角色</el-button
      >
      <el-dialog v-model="addRoleDialogSeen" title="新增角色" append-to-body>
        角色名称：<br />
        <el-input v-model="addRoleName" autocomplete="off" />
        <div style="margin-top: 20px">
          <el-button @click="hideAddRoleDialog()">取消</el-button>
          <el-button type="primary" @click="confirmAddRole()">确认</el-button>
        </div>
      </el-dialog>
    </el-col>
  </el-row>
</template>

<script lang="ts" setup>
import { reactive, Ref, ref } from "vue";
import {
  GetGroupListAPI,
  GetMemberByOrgAPI,
  PostOwnRoleAPI,
  GetRoleByOrgAPI,
  GetAddRoleAPI,
  PostDeleteAllUserOrgRoleAPI,
  GetDeleteRoleAPI,
  PostDeleteUserOrgRoleAPI,
  PostEditRoleAPI,
  PostGrantUserRoleAPI,
  GetRevokeAPI,
  GetUserByNameAPI,
  GetAddInviteMsgAPI,
  PostSetUserRoleToBasicAPI, 
  PostAddUserWithBasicRoleAPI
} from "../request/api";

interface User {
  // 对应后端的MemberDTO
  username: string;
  name: string;
  sex: string;
  roles: string; // 后端强转的字符串
}

interface Role {
  id: string;
  name: string;
}

interface Group {
  id: string;
  name: string;
}

/****************按组织初始化数据******************/
const groupForm = reactive({
  // 组织选择表单
  id: "",
});
const groupList = ref<Group[]>([]); // 选择组织下拉框
const memberList = ref([]); // 成员表格
const roleList = ref([]); // 角色表格

const currentGroupId = ref(""); // 保存当前选择的 groupId

const user = ref({
  username: 'testUser',
  password: '123456',
  email: 'test@example.com',
  // 其他用户信息
});

const setUserRoleToBasic = async () => {
  try {
    const res = await PostSetUserRoleToBasicAPI(user.value);
    console.log(res);
    alert('设置用户角色为 basic 成功');
  } catch (error: any) {
    if (error.response) {
      console.error('服务器响应错误:', error.response.data);
      alert(`设置用户角色为 basic 失败: ${error.response.data.message}`);
    } else if (error.request) {
      console.error('请求已发送，但未收到响应:', error.request);
      alert('设置用户角色为 basic 失败: 没有收到服务器响应');
    } else {
      console.error('请求发生错误:', error.message);
      alert(`设置用户角色为 basic 失败: ${error.message}`);
    }
  }
};

const addUserWithBasicRole = async () => {
  try {
    const res = await PostAddUserWithBasicRoleAPI(user.value);
    console.log(res);
    alert('新增用户并设置为 basic 角色成功');
  } catch (error: any) {
    if (error.response) {
      console.error('服务器响应错误:', error.response.data);
      alert(`新增用户并设置为 basic 角色失败: ${error.response.data.message}`);
    } else if (error.request) {
      console.error('请求已发送，但没有收到响应:', error.request);
      alert('新增用户并设置为 basic 角色失败: 没有收到服务器响应');
    } else {
      console.error('请求发生错误:', error.message);
      alert(`新增用户并设置为 basic 角色失败: ${error.message}`);
    }
  }
};


GetGroupListAPI().then((res) => {
  // 获取选择组织下拉框
  groupList.value = res.data.data;
  console.log(res.data.data);
}).catch((error)=>{
  console.error('获取组织列表失败：',error)
});

// 弹窗可视化绑定
const grantUserDialogSeen = ref<boolean[]>([]);
const revokeUserDialogSeen = ref<boolean[]>([]);
const editRoleDialogSeen = ref<boolean[]>([]);

const memberManageSeen = ref(true); // 如果是班级（或权限不足的管理组管理员）将被隐藏

function initByGroup() {

  const groupId = groupForm.id;
  currentGroupId.value = groupId; // 保存当前选择的 groupId

  // 点击查询，按所选组织查找表格
  GetMemberByOrgAPI("", groupId).then((res) => {
    memberList.value = res.data.data;
    console.log(res);
  }).catch((error=>{
    alert('获取成员列表失败：${error.message}');
    console.error(error);
  }));
  GetRoleByOrgAPI("", groupId).then((res) => {
    roleList.value = res.data.data;
    console.log(res);
  }).catch((error) => {
    alert(`获取角色列表失败: ${error.message}`);
    console.error(error);
  });
  grantUserDialogSeen.value = Array(memberList.value.length).fill(false);
  revokeUserDialogSeen.value = Array(memberList.value.length).fill(false);
  editRoleDialogSeen.value = Array(roleList.value.length).fill(false);
  if (parseInt(groupForm.id) > 10000) {
    memberManageSeen.value = false;
  } else {
    memberManageSeen.value = true; // TODO: 通知组管理员权限控制欠缺
  }
}
/******************授予角色对话窗********************* */
function showGrantUserDialog(index: number) {
  // 打开弹窗
  grantUserDialogSeen.value[index] = true;
}

const grantUserCurrentRoleRow: Ref<Role> = ref({id:"",name:""}); // 当前选定的角色
const changeCurrentGrantRole = (val) => {
  // 改变当前选定的角色
  grantUserCurrentRoleRow.value = val;
  console.log(grantUserCurrentRoleRow);
};

function hideGrantUserDialog(index: number) {
  // 关闭弹窗
  grantUserDialogSeen.value[index] = false;
}

function confirmGrantUser(index: number, row: User) {
  // 确认授予角色
  if (!grantUserCurrentRoleRow.value) {
    alert('请选择要授予的角色');
    return;
  }
  PostGrantUserRoleAPI({
    member: row,
    role: grantUserCurrentRoleRow.value,
    groupId: groupForm.id,
  }).then((res) => {
    console.log(grantUserCurrentRoleRow);
    console.log(res);
    alert('角色授予成功');
    initByGroup(); // 刷新数据
  }).catch((error) => {
    alert(`角色授予失败: ${error.message}`);
    console.error(error);
  });
  grantUserDialogSeen.value[index] = false;
}
/********************收回角色对话窗****************** */
const ownRoleList = ref([]);
function showRevokeUserDialog(index: number, row: User) {
  PostOwnRoleAPI({
    groupId: groupForm.id,
    member: row,
  }).then((res) => {
    ownRoleList.value = res.data.data;
    console.log(res);
  }).catch((error) => {
    alert(`获取用户角色列表失败: ${error.message}`);
    console.error(error);
  });
  revokeUserDialogSeen.value[index] = true;
}

const revokeUserCurrentRow: Ref<Role> = ref({id:"",name:""});
const changeCurrentRevokeRole = (val) => {
  revokeUserCurrentRow.value = val;
};

function hideRevokeUserDialog(index: number) {
  revokeUserDialogSeen.value[index] = false;
}

function confirmRevokeUser(index: number, row: User) {
  if (!revokeUserCurrentRow.value) {
    alert('请选择要收回的角色');
    return;
  }
  if (parseInt(groupForm.id) < 10000) {
    GetRevokeAPI({
      username: row.username,
      groupId: groupForm.id,
      roleId: revokeUserCurrentRow.value.id,
    }).then(() => {
      alert('角色收回成功');
      initByGroup(); // 刷新数据
    }).catch((error) => {
      alert(`角色收回失败: ${error.message}`);
      console.error(error);
    });
  } else {
    alert('当前组织权限不足，无法收回角色');
  }
  revokeUserDialogSeen.value[index] = false;
}
/******************删除成员*******************/
function confirmDeleteUser(row: User) {
  if (confirm('确定要删除该成员吗？')) {
    // TODO: 当前阶段相当于删除该成员在组织中的全部角色
    // 其实可以强制给所有角色赋一个basic身份（超级管理员的问题也可以用这种手段解决）
    // 建议把这个一并弄了。（指新增成员的用例）
    PostDeleteAllUserOrgRoleAPI(row).then((res) => {
      console.log(res);
      alert('成员删除成功');
      initByGroup(); // 强制刷新
    }).catch((error) => {
      alert(`成员删除失败: ${error.message}`);
      console.error(error);
    });
  }
}
/*********************邀请成员******************** */
const inviteMemberDialogSeen = ref(false);

const inviteMemberNameInput = ref("");
const inviteMemberUsernameInput = ref('');

const inviteMemberSearchUserList= ref([]);

//显示邀请成员对话框
function showInviteMemberDialog() {
  inviteMemberDialogSeen.value = true;
  console.log(inviteMemberDialogSeen.value)
}
//隐藏邀请成员对话框
function hideInviteMemberDialog() {
  inviteMemberDialogSeen.value = false;
}

// 按姓名搜索用户
const findUserByname = async () => {
  const name = inviteMemberNameInput.value;
  if (name) {
    try {
      const res = await GetUserByNameAPI(name);
      inviteMemberSearchUserList.value = res.data.data;
    } catch (error) {
      alert('搜索用户失败，请稍后重试！');
      console.error(error);
    }
  }
};

// 邀请用户
const inviteUser = async (username) => {
  const groupId = groupForm.id;
  if (!groupId) {
    alert('请选择要邀请的组织！');
    return;
  }
  try {
    await GetAddInviteMsgAPI({}, groupId, username);
    alert('邀请消息已发送！');
  } catch (error) {
    alert('邀请失败，请稍后重试！');
    console.error(error);
  }
};

// 确认邀请成员
const confirmInviteMemberDialog = async () => {
  const groupId = groupForm.id;
  const username = inviteMemberUsernameInput.value;
  if (!groupId || !username) {
    alert('请选择要邀请的组织并输入用户编号！');
    return;
  }
  try {
    //调用邀请成员的API
    await GetAddInviteMsgAPI({}, groupId, username);
    alert('邀请消息已发送！');
    inviteMemberDialogSeen.value = false;
    hideInviteMemberDialog();
  } catch (error) {
    alert('邀请失败，请稍后重试！');
    console.error('邀请成员失败：',error);
  }
};

/*****************修改角色****************** */
const editRoleForm = reactive({
  id: "",
  name: "",
});

function showEditRoleDialog(index: number, row: Role) {
  editRoleForm.id = row.id;
  editRoleForm.name = row.name; // 初始化表单的角色名称
  editRoleDialogSeen.value[index] = true;
}

function hideEditRoleDialog(index: number) {
  editRoleDialogSeen.value[index] = false;
}

async function confirmEditRole(index: number) {
  try {
    await PostEditRoleAPI(editRoleForm);
    alert("角色修改成功！");
    editRoleDialogSeen.value[index] = false;
    initByGroup(); // 刷新成员列表
  } catch (error) {
    console.error('角色修改失败:', error);
    alert("角色修改失败，请稍后重试！");
  }
}
/*****************删除角色*****************/
async function confirmDeleteRole(row) {
  try {
    const res = await GetDeleteRoleAPI({
      id: row.id,
    });
    console.log(res);
    if (res.data.data === true) {
      alert("删除角色成功！");
    } else {
      alert("删除角色失败！");
    }
    initByGroup(); // 刷新
  } catch (error) {
    console.error('删除角色失败:', error);
    alert("删除角色失败，请稍后重试！");
  }
}
/*******************新增角色 ************* */
const addRoleName = ref('');
const addRoleDialogSeen = ref(false);
function showAddRoleDialog() {
  addRoleDialogSeen.value = true;
}

function hideAddRoleDialog() {
  addRoleDialogSeen.value = false;
}

async function confirmAddRole() {
  if (!addRoleName.value) {
    alert('请输入角色名称！');
    return;
  }
  try {
    await GetAddRoleAPI({
      addRoleName: addRoleName.value,
      groupId: groupForm.id,
    });
    alert("新增角色成功！");
    addRoleDialogSeen.value = false;
    initByGroup(); 
  } catch (error) {
    console.error('新增角色失败:', error);
    alert("新增角色失败，请稍后重试！");
  }
}
</script>
