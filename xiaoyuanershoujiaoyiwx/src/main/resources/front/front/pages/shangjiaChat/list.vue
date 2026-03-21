<template>
	<view class="content">
		<view class="cu-bar bg-white search" :style="[{top: CustomBar + 'px'}]">
			<view class="action">
				<view @tap="showShangjiaPick" :style="{padding:'0 10rpx',lineHeight:'80rpx',height:'80rpx',fontSize:'28rpx',color:'rgba(239,239,239,1)',backgroundColor:btnColor[0]}">选择商家</view>
				<view @tap="loadList" :style="{padding:'0 10rpx',lineHeight:'80rpx',height:'80rpx',fontSize:'28rpx',color:'rgba(239,239,239,1)',backgroundColor:btnColor[1]||'#909399'}">刷新</view>
			</view>
		</view>
		<view v-if="sessionList.length === 0 && !loading" class="empty-tip">暂无咨询记录，点击上方「选择商家」发起咨询</view>
		<view class="session-list">
			<view class="list b-b" v-for="(item, index) in sessionList" :key="index"
				 style="display:table;background-color:#f4f4f4;margin:20rpx;border-radius:20rpx;box-shadow:0 0 9rpx 3rpx #999;width:95%"
				 @tap="toChat(item)">
				<view class="wrapper" style="padding:20rpx;margin-left:15rpx;">
					<view style="font-size:32rpx;display:flex;justify-content:space-between;align-items:center;">
						<text class="cuIcon-service" style="margin-right:15rpx;color:var(--publicMainColor);"></text>
						<b>{{ item.shangjiaName }}</b>
						<text class="cuIcon-right" style="font-size:28rpx;color:#8799A3;"></text>
					</view>
					<view class="address-box" style="padding:10rpx 0;font-size:26rpx;color:#666;">
						<text v-if="item.lastText">{{ item.lastText.length > 25 ? item.lastText.slice(0,25) + '...' : item.lastText }}</text>
						<text v-else>点击进入聊天</text>
					</view>
					<text style="font-size:22rpx;color:#8799A3;">{{ item.lastTime }}</text>
				</view>
			</view>
		</view>
		<!-- 选择商家弹层 -->
		<uni-popup ref="shangjiaPopup" type="bottom">
			<view class="popup-content">
				<view class="popup-title">选择商家发起咨询</view>
				<scroll-view scroll-y class="popup-list">
					<view class="popup-item" v-for="(s, i) in shangjiaOptions" :key="i" @tap="pickShangjia(s)">
						<text>{{ s.shangjiaName }}</text>
						<text class="cuIcon-right"></text>
					</view>
					<view v-if="shangjiaOptions.length === 0 && !popupLoading" class="popup-empty">暂无商家</view>
				</scroll-view>
				<view class="popup-close" @tap="closeShangjiaPick">关闭</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				user: {},
				btnColor: ['#67c23a', '#909399', '#e6a23c'],
				sessionList: [],
				loading: false,
				CustomBar: 0,
				shangjiaOptions: [],
				popupLoading: false
			};
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		onShow() {
			this.btnColor = this.btnColor.sort(() => 0.5 - Math.random());
			this.loadList();
		},
		onLoad() {
			let table = uni.getStorageSync("nowTable");
			this.$api.session(table).then(res => {
				this.user = res.data;
			});
		},
		methods: {
			async loadList() {
				this.loading = true;
				try {
					let res = await this.$api.page('shangjiaChat', { page: 1, limit: 200, sort: 'id', order: 'desc' });
					let list = (res.data && res.data.list) ? res.data.list : [];
					// 按商家分组，取每条会话最后一条记录
					let map = {};
					list.forEach(row => {
						let sid = row.shangjiaId;
						if (!map[sid]) {
							map[sid] = {
								shangjiaId: row.shangjiaId,
								shangjiaName: row.shangjiaName || '商家',
								lastText: row.shangjiaChatReplyText || row.shangjiaChatIssueText || '',
								lastTime: row.replyTime || row.issueTime || row.insertTime || row.createTime || ''
							};
						} else {
							let t = row.replyTime || row.issueTime || row.insertTime || row.createTime || '';
							if (t > map[sid].lastTime) {
								map[sid].lastText = row.shangjiaChatReplyText || row.shangjiaChatIssueText || '';
								map[sid].lastTime = t;
							}
						}
					});
					this.sessionList = Object.values(map).sort((a, b) => (b.lastTime > a.lastTime ? 1 : -1));
				} catch (e) {
					this.$utils.msg('加载失败');
					this.sessionList = [];
				}
				this.loading = false;
			},
			toChat(item) {
				uni.navigateTo({
					url: './chat?shangjiaId=' + item.shangjiaId + '&shangjiaName=' + encodeURIComponent(item.shangjiaName || '商家')
				});
			},
			showShangjiaPick() {
				this.$refs.shangjiaPopup.open();
				this.shangjiaOptions = [];
				this.popupLoading = true;
				this.$api.page('shangjia', { page: 1, limit: 100 }).then(res => {
					this.shangjiaOptions = (res.data && res.data.list) ? res.data.list : [];
					this.popupLoading = false;
				}).catch(() => { this.popupLoading = false; });
			},
			closeShangjiaPick() {
				this.$refs.shangjiaPopup.close();
			},
			pickShangjia(s) {
				this.$refs.shangjiaPopup.close();
				this.toChat({ shangjiaId: s.id, shangjiaName: s.shangjiaName });
			}
		}
	};
</script>

<style lang="scss" scoped>
	.content { padding-top: 20rpx; min-height: 100vh; background: #f5f5f5; }
	.empty-tip { text-align: center; padding: 80rpx 40rpx; color: #999; font-size: 28rpx; }
	.session-list { padding-bottom: 30rpx; }
	.popup-content { background: #fff; border-radius: 24rpx 24rpx 0 0; max-height: 70vh; padding: 24rpx; }
	.popup-title { text-align: center; font-size: 32rpx; font-weight: bold; margin-bottom: 20rpx; }
	.popup-list { max-height: 50vh; }
	.popup-item { display: flex; justify-content: space-between; align-items: center; padding: 28rpx 20rpx; border-bottom: 1rpx solid #eee; font-size: 28rpx; }
	.popup-empty { text-align: center; padding: 40rpx; color: #999; font-size: 26rpx; }
	.popup-close { text-align: center; padding: 24rpx; font-size: 28rpx; color: var(--publicMainColor); }
</style>
