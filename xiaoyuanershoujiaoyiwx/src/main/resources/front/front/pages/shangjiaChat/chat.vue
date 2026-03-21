<template>
	<view class="chat-page">
		<scroll-view scroll-y class="msg-list" :scroll-into-view="scrollToId" scroll-with-animation>
			<view v-for="(msg, index) in messageList" :id="'msg' + index" :key="index" class="msg-row" :class="msg.isUser ? 'msg-right' : 'msg-left'">
				<view class="bubble">
					<text class="msg-text">{{ msg.text }}</text>
					<text class="msg-time">{{ msg.time }}</text>
				</view>
			</view>
			<view v-if="messageList.length === 0 && !loading" class="empty-tip">暂无消息，请输入内容发送</view>
		</scroll-view>
		<view class="input-bar">
			<input class="input" v-model="inputText" placeholder="输入咨询内容" :adjust-position="true" @confirm="send" />
			<view class="send-btn" @tap="send">发送</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				shangjiaId: '',
				shangjiaName: '商家',
				user: {},
				messageList: [],
				inputText: '',
				loading: false,
				scrollToId: ''
			};
		},
		onLoad(options) {
			this.shangjiaId = options.shangjiaId || '';
			this.shangjiaName = decodeURIComponent(options.shangjiaName || '商家');
			uni.setNavigationBarTitle({ title: this.shangjiaName });
			let table = uni.getStorageSync("nowTable");
			this.$api.session(table).then(res => {
				this.user = res.data;
				this.loadMessages();
			});
		},
		methods: {
			async loadMessages() {
				if (!this.shangjiaId) return;
				this.loading = true;
				try {
					let res = await this.$api.page('shangjiaChat', {
						page: 1,
						limit: 200,
						shangjiaId: this.shangjiaId,
						sort: 'id',
						order: 'asc'
					});
					let list = (res.data && res.data.list) ? res.data.list : [];
					let messages = [];
					list.forEach(row => {
						if (row.shangjiaChatIssueText) {
							messages.push({
								isUser: true,
								text: row.shangjiaChatIssueText,
								time: row.issueTime || row.insertTime || ''
							});
						}
						if (row.shangjiaChatReplyText) {
							messages.push({
								isUser: false,
								text: row.shangjiaChatReplyText,
								time: row.replyTime || ''
							});
						}
					});
					this.messageList = messages;
					this.$nextTick(() => {
						if (this.messageList.length > 0) {
							this.scrollToId = 'msg' + (this.messageList.length - 1);
						}
					});
				} catch (e) {
					this.$utils.msg('加载消息失败');
				}
				this.loading = false;
			},
			async send() {
				let text = (this.inputText || '').trim();
				if (!text) {
					this.$utils.msg('请输入内容');
					return;
				}
				if (!this.user || !this.user.id) {
					this.$utils.msg('请先登录');
					return;
				}
				try {
					await this.$api.add('shangjiaChat', {
						yonghuId: this.user.id,
						shangjiaId: parseInt(this.shangjiaId),
						shangjiaChatIssueText: text
					});
					this.inputText = '';
					this.$utils.msg('发送成功');
					this.loadMessages();
				} catch (e) {
					this.$utils.msg('发送失败');
				}
			}
		}
	};
</script>

<style lang="scss" scoped>
	.chat-page { display: flex; flex-direction: column; height: 100vh; background: #f0f0f0; }
	.msg-list { flex: 1; padding: 20rpx; box-sizing: border-box; }
	.msg-row { margin-bottom: 24rpx; }
	.msg-right { display: flex; justify-content: flex-end; }
	.msg-left { display: flex; justify-content: flex-start; }
	.bubble { max-width: 75%; padding: 20rpx 24rpx; border-radius: 16rpx; }
	.msg-right .bubble { background: var(--publicMainColor); color: #fff; border-bottom-right-radius: 4rpx; }
	.msg-left .bubble { background: #fff; color: #333; border-bottom-left-radius: 4rpx; box-shadow: 0 1rpx 4rpx rgba(0,0,0,0.08); }
	.msg-text { font-size: 28rpx; word-break: break-all; display: block; }
	.msg-time { font-size: 22rpx; opacity: 0.8; margin-top: 8rpx; display: block; }
	.empty-tip { text-align: center; color: #999; font-size: 26rpx; padding: 40rpx; }
	.input-bar { display: flex; align-items: center; padding: 16rpx 20rpx; background: #fff; border-top: 1rpx solid #eee; }
	.input { flex: 1; height: 72rpx; padding: 0 24rpx; font-size: 28rpx; background: #f5f5f5; border-radius: 36rpx; margin-right: 20rpx; }
	.send-btn { padding: 0 32rpx; height: 72rpx; line-height: 72rpx; font-size: 28rpx; color: #fff; background: var(--publicMainColor); border-radius: 36rpx; }
</style>
